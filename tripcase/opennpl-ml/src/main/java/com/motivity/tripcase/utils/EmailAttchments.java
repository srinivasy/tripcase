package com.motivity.tripcase.utils;

import java.io.File;
import java.io.IOException;

public class EmailAttchments {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private void extractEmlAttachments(File file) throws IOException {
		MailMessage msg = MailMessage.load(file.getPath());
		// Initialize AttachmentCollection object with MailMessage Attachments
		AttachmentCollection attachments = msg.getAttachments();
		// Iterate over the AttachmentCollection

		for (int index = 0; index < attachments.size(); index++) {
			// Initialize Attachment object and Get the indexed Attachment
			// reference
			Attachment attachment = (Attachment) attachments.get_Item(index);
			// Display Attachment Name
			System.out.println(attachment.getName());
			// Save Attachment to disk
			ClassLoader classLoader = getClass().getClassLoader();
			attachment
					.save((new File(classLoader.getResource(
							"emails/all_email_templates_output-1").getFile())
							.getPath()) + "\\" + attachment.getName());
		}

	}

}
