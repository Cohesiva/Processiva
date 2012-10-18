package com.cohesiva.processes.jbpm.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

/*
 * Domain specific node used for getting a value from a spreadsheet from google docs
 */
public class GetSpreadsheetValueWorkItemHandler extends BaseAsynchronousWorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		new Thread(new Runnable() {

			private WorkItem workItem;

			public Runnable setData(WorkItem workItem) {
				this.workItem = workItem;
				return this;
			}

			public void run() {
				try {
					System.out.println("inside");

					String valueToSendToForm = null;

					SpreadsheetService service = new SpreadsheetService(
							"MySpreadsheetIntegration-v1");

					String accessToken = "1/gC9y-YwAli-YuYHhvybt2gMhCO2O3OJDCwhHrGYTRIs";
					String accessTokenSecret = "h8-A2d7QTJno_Sd-QTVWQeBY";

					String appKey = "343113720444.apps.googleusercontent.com";
					String appSecret = "_9AlBI6Wuqzaa8Js75Fox6Bb";

					GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
					oauthParameters.setOAuthToken(accessToken);
					oauthParameters.setOAuthTokenSecret(accessTokenSecret);
					oauthParameters.setOAuthConsumerKey(appKey);

					OAuthSigner signer;
					oauthParameters.setOAuthConsumerSecret(appSecret);
					signer = new OAuthHmacSha1Signer();

					service.setOAuthCredentials(oauthParameters, signer);

					// Define the URL to request.
					URL SPREADSHEET_FEED_URL;

					SPREADSHEET_FEED_URL = new URL(
							"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

					// { Make a request to the API and get all spreadsheets.
					SpreadsheetFeed feed = service.getFeed(
							SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
					List<SpreadsheetEntry> spreadsheets = feed.getEntries();
					// }

					// { Iterate through all of the spreadsheets returned to get
					// "test"
					// spreadsheet.
					for (SpreadsheetEntry spreadsheet : spreadsheets) {
						System.out.println(spreadsheet.getTitle()
								.getPlainText());

						if (spreadsheet.getTitle().getPlainText()
								.equals("test")) {

							// { Get the first worksheet of the first
							// spreadsheet.
							WorksheetFeed worksheetFeed = service.getFeed(
									spreadsheet.getWorksheetFeedUrl(),
									WorksheetFeed.class);
							List<WorksheetEntry> worksheets = worksheetFeed
									.getEntries();
							WorksheetEntry worksheet = worksheets.get(0);
							// }

							// Fetch first row.
							URL cellFeedUrl = new URI(worksheet
									.getCellFeedUrl().toString()
									+ "?min-row=1&max-row=1").toURL();
							CellFeed cellFeed = service.getFeed(cellFeedUrl,
									CellFeed.class);

							// { Iterate through each cell, printing its value.
							for (CellEntry cell : cellFeed.getEntries()) {
								// Print the cell's address in A1 notation
								System.out.print(cell.getTitle().getPlainText()
										+ "\t");
								// Print the cell's address in R1C1 notation
								System.out.print(cell.getId().substring(
										cell.getId().lastIndexOf('/') + 1)
										+ "\t");
								// Print the cell's formula or text value
								System.out.print(cell.getCell().getInputValue()
										+ "\t");
								// Print the cell's calculated value if the
								// cell's value
								// is numeric
								// Prints empty string if cell's value is not
								// numeric
								System.out.print(cell.getCell()
										.getNumericValue() + "\t");
								// Print the cell's displayed value (useful if
								// the cell
								// has a formula)
								System.out.println(cell.getCell().getValue()
										+ "\t");

								// set value to send to user form
								valueToSendToForm = cell.getCell().getValue();
							}
							// }

							break;
						}
					}
					// }

					Map<String, Object> data = new HashMap<String, Object>();
					data.put("cellValue", valueToSendToForm);
					data.put("body", "Do zaakceptowania wartosc: "
							+ valueToSendToForm);

					System.out.println("end");

					// Notify manager that work item has been completed, pass
					// data
					ksession.getWorkItemManager().completeWorkItem(
							workItem.getId(), data);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ServiceException e) {
					e.printStackTrace();
				} catch (OAuthException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}.setData(workItem)).start();
	}

}