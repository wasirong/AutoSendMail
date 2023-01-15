package com.dhl.yxg.util;


import com.dhl.yxg.data.EmailInfo;
import com.dhl.yxg.data.ResultMessage;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {
    public ResultMessage sendEmail(EmailInfo emailInfo) {

        ResultMessage resultMessage = new ResultMessage();

        Properties properties = new Properties();
//        properties.put("mail.smtp.ssl.enable", true); // 指定使用SSL方式发送
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", emailInfo.getHost());
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties);
        Message emailMessage = new MimeMessage(session);

        try {
            //发送人
            InternetAddress sender = new InternetAddress(emailInfo.getFrom()); //发送者账号
            sender.setPersonal(MimeUtility.encodeText(emailInfo.getNickname()));//昵称
            emailMessage.setFrom(sender);

            //收件人
//            InternetAddress to = new InternetAddress(emailInfo.getAddress());//收件人账号
//            emailMessage.setRecipient(Message.RecipientType.TO, to);

            String[] addressList = emailInfo.getAddress().split(";");
            InternetAddress[] sendTo = new InternetAddress[addressList.length];

            for (int i = 0; i < sendTo.length; i++) {
                sendTo[i] = new InternetAddress(addressList[i]);
            }
            emailMessage.setRecipients(Message.RecipientType.TO, sendTo);

            //消息
            emailMessage.setSubject(MimeUtility.encodeText(emailInfo.getSubject()));//邮件主题
            emailMessage.setSentDate(new Date());
            MimeMultipart mimeMultipart = new MimeMultipart("mixed");//指定为混合关系
            emailMessage.setContent(mimeMultipart);

            //邮件内容
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailInfo.getContent(), "text/html;charset=UTF-8");

            //组装内容
            mimeMultipart.addBodyPart(htmlPart);

            //组装附件
            String filePath = emailInfo.getFilePath();
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

            emailMessage.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(emailInfo.getHost(), Integer.parseInt(emailInfo.getPort()), emailInfo.getFrom(), emailInfo.getPassword());
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            System.out.println("邮件发送成功！");
            resultMessage.setOk("邮件发送成功！");
        } catch (AddressException e) {
            System.out.println("请检查邮箱地址:" + e.getMessage());
            resultMessage.setError("请检查邮箱地址:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("转码异常，请查看详细信息" + e.getMessage());
            resultMessage.setError("转码异常，请查看详细信息" + e.getMessage());
        } catch (NoSuchProviderException e) {
            System.out.println("邮件发送失败，详细错误原因：" + e.getMessage());
            resultMessage.setError("邮件发送失败，详细错误原因：" + e.getMessage());
        } catch (MessagingException e) {
            System.out.println("邮件发送失败，有可能的问题为服务器url不存在或附件不存在，具体请看详细信息：" + e.getMessage());
            resultMessage.setError("邮件发送失败，MessagingException：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("例外发生:" + e.getMessage());
        }

        return resultMessage;
    }
}
