package com.project.login.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.project.login.Binding.Login;
import com.project.login.Binding.LoginBinding;
import com.project.login.Binding.LoginIdPass;
import com.project.login.Constance.AppConstants;
import com.project.login.Dto.Dto;
import com.project.login.Entity.LoginEntity;
import com.project.login.EntityRepo.LoginEntityRepo;
import com.project.login.Properties.AppProperties;
import com.project.login.ServiceRepo.ServiceRepo;
import com.project.login.Util.EmailUtils;

@Service
public class LoginService implements ServiceRepo {

	public LoginEntityRepo repo;
	public AppProperties prop;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	private EmailUtils emailUtils;

	public LoginService(LoginEntityRepo repo, AppProperties prop, EmailUtils emailUtils) {
		super();
		this.repo = repo;
		this.prop = prop;
		this.emailUtils = emailUtils;
	}

	Logger logger = LogManager.getLogger(LoginService.class);

	@Override
	public Boolean create(LoginBinding login) {
		logger.info("post method started");
		Dto dto = new Dto();
		Optional<LoginEntity> byEmail = repo.findByEmail(login.getEmailId());
		if (byEmail.isEmpty()) {
			LoginEntity log = new LoginEntity();
			log.setFullName(login.getFullName());
			log.setDateOfBirth(login.getDateOfBirth());
			log.setEmailId(login.getEmailId());
			log.setGender(login.getGender());
			log.setMobileNum(login.getMobileNum());
			log.setSsn(login.getSsn());
			log.setAccStatus(false);

			log.setTempPassword(tempPassword());

			BeanUtils.copyProperties(log, dto);
			LoginEntity save = repo.save(log);

			String subject = "Your Registration is Success";
			String fileName = "REG-EMAIL-BODY.txt";
			String body = readEmailBody(log.getFullName(), log.getTempPassword(), fileName);
			emailUtils.sendMail(login.getEmailId(), subject, body);

			return save.getLoginId() != null;
		}
		logger.error(prop.getMessages().get(AppConstants.EMAIL_EXIST));
		return false;
	}

	@Override
	public String activateUser(Dto dto) {

		String email = dto.getEmailId();

		if (email == null || email.isEmpty()) {
			logger.error(prop.getMessages().get(AppConstants.EMAIL_NULL));
			return prop.getMessages().get(AppConstants.EMAIL_NULL);
		}

		Optional<LoginEntity> byEmail = repo.findByEmail(email);

		if (byEmail.isPresent()) {
			LoginEntity login = byEmail.get();

			if (dto.getTempPassword().equals(login.getTempPassword()) && dto.getTempPassword() != null) {
				// login.setPassword(dto.getPassword());
				login.setAccStatus(true);
				login.setTempPassword(null);

				String encoded = Base64.getEncoder().encodeToString(dto.getPassword().getBytes());
				login.setPassword(encoded);

				repo.save(login);
				logger.info(prop.getMessages().get(AppConstants.NEW_PASSWORD_CREATED));
				return prop.getMessages().get(AppConstants.NEW_PASSWORD_CREATED);
			} else {
				return prop.getMessages().get(AppConstants.WRONG_TEMP_PASS);
			}
		}

		return prop.getMessages().get(AppConstants.EMAIL_NOT_FOUND);
	}

	@Override
	public String forgetPass(Dto dto) {

		Optional<LoginEntity> byEmail = repo.findByEmail(dto.getEmailId());
		if (byEmail.isPresent()) {

			LoginEntity log = byEmail.get();
			log.setTempPassword(tempPassword());
			logger.info(prop.getMessages().get(AppConstants.NEW_PASSWORD_CREATED));

			LoginIdPass l = new LoginIdPass();
			BeanUtils.copyProperties(log, l);
			repo.save(log);
			String subject = "Recovery Password";
			String fileName = "RECOVER-PWD-BODY.txt";
			String body = readEmailBody(log.getFullName(), log.getTempPassword(), fileName);
			boolean sendMail = emailUtils.sendMail(dto.getEmailId(), subject, body);

			if (sendMail) {
				return prop.getMessages().get(AppConstants.TEMPPASS_SENT_TO_EMAIL);
			}
			return null;

		}
		return null;
	}

	public String tempPassword() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String pass = upperAlphabet + lowerAlphabet + numbers;

		int length = 6;
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(pass.length());
			char randomChar = pass.charAt(index);
			sb.append(randomChar);
		}
		String randomString = sb.toString();

		return randomString;
	}

	private String readEmailBody(String fullname, String pwd, String filename) {
		String url = "http://localhost:8082/swagger-ui/index.html#/login-contoller/activateUserAccount";
		String mailBody = null;

		try (FileReader fr = new FileReader(filename); BufferedReader br = new BufferedReader(fr);) {
			StringBuilder builder = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			br.close();
			mailBody = builder.toString();
			mailBody = mailBody.replace("{FULL-NAME}", fullname);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{http://localhost:8082/setPass}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return mailBody;
	}

	@Override
	public String Login(Login login) {

		Optional<LoginEntity> byEmail = repo.findByEmail(login.getEmailId());
		if (byEmail.isPresent()) {
			LoginEntity loginEntity = byEmail.get();
			byte[] decodedBytes = Base64.getDecoder().decode(loginEntity.getPassword());
			String decoded = new String(decodedBytes);
			if (login.getPassword().equals(decoded)) {
				if (login.getPassword().equals(decoded) && loginEntity.getAccStatus() != true) {
					return prop.getMessages().get(AppConstants.LOGIN_BUT_ACC_INACTIVE);
				}
				return prop.getMessages().get(AppConstants.LOGIN_SUCC);
			} else {
				return prop.getMessages().get(AppConstants.WRONG_PASS);
			}
		} else {
			return prop.getMessages().get(AppConstants.EMAIL_NOT_FOUND);
		}
	}

	@Override
	public LoginBinding getById(Integer id) {
		Optional<LoginEntity> byId = repo.findById(id);
		if (byId.isPresent()) {
			LoginBinding log = new LoginBinding();
			LoginEntity login = byId.get();
			BeanUtils.copyProperties(login, log);
			return log;
		}
		return null;
	}

	@Override
	public List<LoginBinding> getAll() {
		List<LoginEntity> all = repo.findAll();
		List<LoginBinding> log = new ArrayList<>();

		for (LoginEntity entity : all) {
			LoginBinding binding = new LoginBinding();
			BeanUtils.copyProperties(entity, binding);
			log.add(binding);
		}

		return log;
	}

	@Override
	public String deleteById(Integer id) {
		Optional<LoginEntity> byId = repo.findById(id);
		if (byId.isPresent()) {
			repo.deleteById(id);
			return prop.getMessages().get(AppConstants.RECORD_DELETED);
		}
		return prop.getMessages().get(AppConstants.ID_NOT_FOUND);
	}

}
