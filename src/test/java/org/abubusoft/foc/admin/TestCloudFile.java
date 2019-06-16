package org.abubusoft.foc.admin;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.services.CloudFileService;
import org.abubusoft.foc.services.ConsumersService;
import org.abubusoft.foc.services.UploaderService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.firebase.auth.FirebaseAuthException;

public class TestCloudFile extends BaseTest {

	@Autowired
	protected CloudFileService cloudFileService;

	@Autowired
	protected ConsumersService consumerService;

	@Autowired
	protected UploaderService uploaderService;

	private Consumer createConsumer(long number) {
		String displayName = "Consumer #"+number;
		String username = "consumer-" + System.currentTimeMillis() + "@gmail.com";
		String email = "uxcesco@gmail.com";
		String password = "password";

		Consumer user = new Consumer();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		user.setCodiceFiscale("" + System.currentTimeMillis());

		return consumerService.createUser(user, password);
	}

	private Uploader createUploader(long number) throws FileNotFoundException, IOException {
		String displayName = "Uploader #"+number;
		String username = "uploader-" + System.currentTimeMillis() + "@gmail.com";
		String email = "uxcesco@gmail.com";
		String password = "password";

		File image = new File("src/test/resources/images/user.png");

		Uploader user = new Uploader();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		user.setImage(IOUtils.toByteArray(new FileInputStream(image)));

		return uploaderService.createUser(user, password);
	}

	@Test
	public void testCreateFileNoTag() throws FirebaseAuthException, IOException {
		Consumer consumer = createConsumer(1);
		Uploader uploader = createUploader(1);

		File file = new File("src/test/resources/images/user.png");
		byte[] content = IOUtils.toByteArray(new FileInputStream(file));

		cloudFileService.uploadFile(uploader.getUsername(), consumer, file.getName(), content, null);
	}

	@Test
	public void testCreateFile() throws FirebaseAuthException, IOException {
		Consumer consumer = createConsumer(1);
		Uploader uploader = createUploader(1);

		createCloudFile(consumer, uploader, new File("src/test/resources/images/user.png"), null);
	}

	@Test
	public void testReadFile() throws FirebaseAuthException, IOException {
		Consumer consumer = createConsumer(1);
		Uploader uploader = createUploader(1);

		CloudFile file = createCloudFile(consumer, uploader, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		Pair<CloudFile, byte[]> fileResult = cloudFileService.getFile(consumer.getUsername(), file.getUuid());

		assertEquals(file.getContentLength(), fileResult.getFirst().getContentLength());
	}

	@Test
	public void testConsumer2ListaUploader() throws FirebaseAuthException, IOException {
		ObjectMapper objMapper = new ObjectMapper();

		Consumer consumer1 = createConsumer(1);
		Consumer consumer2 = createConsumer(2);
		Consumer consumer3 = createConsumer(3);
		Consumer consumer4 = createConsumer(4);
		
		Uploader uploader1 = createUploader(1);
		Uploader uploader2 = createUploader(2);
		Uploader uploader3 = createUploader(3);

		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer4, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer2, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		createCloudFile(consumer1, uploader2, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		List<CloudFile> result = cloudFileService.findByConsumerAndUploader(consumer1.getId(), uploader1.getId());

		assertEquals(result.size(), 3);

		{
			List<UploaderSummary> summary = uploaderService.reportCloudFileForAllUploaders(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31));
			for (UploaderSummary item : summary) {
				logger.info(objMapper.writeValueAsString(item));
			}
		}
		
		{
			List<UploaderDetailSummary> summary = uploaderService.reportConsumerForAllUploaders(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31));
			for (UploaderDetailSummary item : summary) {
				logger.info(objMapper.writeValueAsString(item));
			}
		}
				
	}
	
	@Test
	public void testConsumerReport() throws FirebaseAuthException, IOException {
		ObjectMapper objMapper = new ObjectMapper();

		Consumer consumer1 = createConsumer(1);
		Consumer consumer2 = createConsumer(2);
		Consumer consumer3 = createConsumer(3);
		Consumer consumer4 = createConsumer(4);
		
		Uploader uploader1 = createUploader(1);
		Uploader uploader2 = createUploader(2);
		Uploader uploader3 = createUploader(3);

		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer4, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer2, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		createCloudFile(consumer1, uploader2, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		List<CloudFile> result = cloudFileService.findByConsumerAndUploader(consumer1.getId(), uploader1.getId());

		assertEquals(result.size(), 3);
			
		{
			List<UploaderDetailSummary> summary = consumerService.reportConsumerForUploader(consumer1.getId());
			for (UploaderDetailSummary item : summary) {
				logger.info(objMapper.writeValueAsString(item));
			}
		}		
	}
	
	@Test
	public void testListUploader() throws FirebaseAuthException, IOException {
		ObjectMapper objMapper = new ObjectMapper();

		Consumer consumer1 = createConsumer(1);
		Consumer consumer2 = createConsumer(2);
		Consumer consumer3 = createConsumer(3);
		Consumer consumer4 = createConsumer(4);
		
		Uploader uploader1 = createUploader(1);
		Uploader uploader2 = createUploader(2);
		Uploader uploader3 = createUploader(3);

		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer1, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer4, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer2, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));
		createCloudFile(consumer3, uploader1, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

		createCloudFile(consumer1, uploader2, new File("src/test/resources/files/fruits.json"),
				Sets.newHashSet("frutta", "json"));

					
		{
			List<Uploader> summary = uploaderService.findByConsumer(consumer1.getId());
			for (Uploader item : summary) {
				logger.info(objMapper.writeValueAsString(item));
			}
		}		
	}

	private CloudFile createCloudFile(Consumer consumer, Uploader uploader, File file, Set<String> tags)
			throws IOException, FileNotFoundException {
		byte[] content = IOUtils.toByteArray(new FileInputStream(file));

		return cloudFileService.uploadFile(uploader.getUsername(), consumer, file.getName(), content, tags);
	}

	

}
