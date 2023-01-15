package com.dhl.yxg.util;

import com.dhl.yxg.data.EmailInfo;
import com.dhl.yxg.data.ResultMessage;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtilpsd {
    public ResultMessage sendEmail(EmailInfo emailInfo) {
        ResultMessage rs = new ResultMessage();
        try {

            //1.创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();                    //参数配置
            props.setProperty("mail.transport.protocol", "smtp");   //使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", emailInfo.getHost());   //发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            //需要请求认证

            //2.根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            //session.setDebug(true);                                 //设置为debug模式,可以查看详细的发送log

            //3.创建一封邮件
            MimeMessage message = null;

            message = createMimeMessage(session, emailInfo);

            //4.根据 Session获取邮件传输对象
            Transport transport = session.getTransport();

            // 5.使用邮箱账号和密码连接邮件服务器, 这里认证的邮箱必须与 message中的发件人邮箱一致,否则报错
            //
            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1)邮箱没有开启 SMTP 服务;
            //           (2)邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3)邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4)请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5)如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            //PS_03:仔细看log,认真看log,看懂log,错误原因都在log已说明。
            transport.connect(emailInfo.getFrom(), emailInfo.getPassword());
            //6.发送邮件,发到所有的收件地址,message.getAllRecipients()获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("邮件发送成功");
            // 7. 关闭连接
            transport.close();
            rs.setOk("邮件发送成功");
        } catch (Exception e) {
            rs.setError("例外发生：" + e.getMessage());
        }
        return rs;
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, EmailInfo em) throws Exception {
        // 1.创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2.From:发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(em.getFrom(), "yxg", "UTF-8"));
        // 3.To:收件人（可以增加多个收件人、抄送、密送）

        String[] addressList = em.getAddress().split(";");
        InternetAddress[] sendTo = new InternetAddress[addressList.length];

        for (int i = 0; i < sendTo.length; i++) {
            sendTo[i] = new InternetAddress(addressList[i]);
        }
        message.setRecipients(Message.RecipientType.TO, sendTo);
//        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "ly", "UTF-8"));
        // 4.Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(em.getSubject(), "UTF-8");
        // 5.Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
//        message.setContent(new EmailInfo().getContent(), "text/html;charset=UTF-8");
        // 6.设置发件时间
        message.setSentDate(new Date());
        MimeMultipart mimeMultipart = new MimeMultipart("mixed");//指定为混合关系
        message.setContent(mimeMultipart);

        //邮件内容
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(em.getContent(), "text/html;charset=UTF-8");

        //组装内容
        mimeMultipart.addBodyPart(htmlPart);

        //组装附件
        String filePath = em.getFilePath();
        if (!"".equals(filePath)) {
            //ToDo 此处可携带多个附件，以|分割****************
            if (!filePath.contains("|")) {
                MimeBodyPart file = new MimeBodyPart();
                FileDataSource file_datasource = new FileDataSource(filePath);
                DataHandler dh = new DataHandler(file_datasource);
                file.setDataHandler(dh);
                //附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码
                file.setFileName(MimeUtility.encodeText(dh.getName()));
                mimeMultipart.addBodyPart(file);
            } else {
                String[] filePaths = filePath.split("\\|");
                for (int i = 0; i < filePaths.length; i++) {
                    MimeBodyPart file = new MimeBodyPart();
                    FileDataSource file_datasource = new FileDataSource(filePaths[i]);
                    DataHandler dh = new DataHandler(file_datasource);
                    file.setDataHandler(dh);
                    //附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码
                    file.setFileName(MimeUtility.encodeText(dh.getName()));
                    mimeMultipart.addBodyPart(file);
                }
            }
        }
        // 7.保存设置
        message.saveChanges();
        return message;
    }
}
