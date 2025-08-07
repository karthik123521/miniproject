package com.project.cat.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cat.Binding.CaseWorkerBinding;
import com.project.cat.Binding.Login;
import com.project.cat.Entity.CaseWorker;
import com.project.cat.EntityRepo.CaseWorkerRepo;
import com.project.cat.Properties.AppProperties;
import com.project.cat.emailConfig.EmailUtility;

@Service
public class CaseworkerServiceImpl implements CaseWorkerService{

	private Logger logger = LoggerFactory.getLogger(CaseworkerServiceImpl.class);

	@Autowired
	private CaseWorkerRepo caseWorkerRepo;
	
	public AppProperties prop;

	@Autowired
	private EmailUtility emailUtility;

	

	@Override
	public List<CaseWorker> getAllUsers() {
		
		return caseWorkerRepo.findAll();
	}

	@Override
	public CaseWorker getUserById(String userId) {
		return caseWorkerRepo.findById(userId).map(entity->{
			return entity;
		}).orElse(null);
		
	}

	@Override
	public boolean deleteUserById(String userId) {
		return caseWorkerRepo.findById(userId).map(entity->{
			caseWorkerRepo.deleteById(entity.getUserId());
			return true;
		}).orElse(false);
	}

	@Override
	public String login(Login login) {
		return caseWorkerRepo.findByEmailAndPassword(login.getEmail(), login.getPassword())
		.map(entity->{
			return "Logined suucessFully";
		}).orElse("Invalid Credentials");
		
	}
	@Override
	public String saveCaseworker(CaseWorkerBinding caseWorkerBinding) {
		CaseWorker caseWorker = new CaseWorker();
		BeanUtils.copyProperties(caseWorkerBinding, caseWorker);
		CaseWorker save = caseWorkerRepo.save(caseWorker);
		if (save != null) {
			String fileName="Reg-email-body.txt";
			String emailBody = readEmailBody(save.getFullName(),save.getPassword(),fileName);
			String subject = "Your Registration is Success";
			emailUtility.sendMail(save.getEmail(),subject, emailBody);
			return "Successfully saved";
		}
		else
			return "Failed to save the CaseWorker";
	}
	
	
	@Override
	public String forgotPwd(String email)  {

		Optional<CaseWorker> byEmail = caseWorkerRepo.findByEmail(email);
		if (byEmail.isPresent()) {

			CaseWorker caseWorker = byEmail.get();
			String subject = "Forgot Password";
			String fileName = "RECOVER-PWD-BODY.txt";
			String body = readEmailBody(caseWorker.getFullName(), caseWorker.getPassword(), fileName);
			boolean sendMail = emailUtility.sendMail(caseWorker.getEmail(), subject, body);

			if (sendMail) {
				return "Password sent to your registered mail";
			}
			return null;

		}
		return "Email not found";
	}

	@Override
	public boolean changeAccountStatus(String userId, Boolean accStatus) {
		return caseWorkerRepo.findById(userId).map(entity -> {
			entity.setAccStatus(accStatus);
			caseWorkerRepo.save(entity);
			return true;
		}).orElse(false);
	}

	@Override
	public String updateCaseWorker(String userId, CaseWorkerBinding caseWorkerBinding) {
		return caseWorkerRepo.findById(userId).
		map(entity->{
			  if (caseWorkerBinding.getFullName() != null && !caseWorkerBinding.getFullName().equalsIgnoreCase(entity.getFullName())) {
		            entity.setFullName(caseWorkerBinding.getFullName());
		        }

		        if (caseWorkerBinding.getEmail() != null && !caseWorkerBinding.getEmail().equalsIgnoreCase(entity.getEmail())) {
		            entity.setEmail(caseWorkerBinding.getEmail());
		        }

		        if (caseWorkerBinding.getMobile() != null && !caseWorkerBinding.getMobile().equals(entity.getMobile())) {
		            entity.setMobile(caseWorkerBinding.getMobile());
		        }
		        if (caseWorkerBinding.getGender() != null && !entity.getGender().equals(caseWorkerBinding.getGender())) {
	                entity.setGender(caseWorkerBinding.getGender());
	            }
	            if (caseWorkerBinding.getDateOfBirth() != null && !entity.getDateOfBirth().equals(caseWorkerBinding.getDateOfBirth())) {
	                entity.setDateOfBirth(caseWorkerBinding.getDateOfBirth());
	            }
	            // Update password and account status without null checks
	            if (!entity.getPassword().equals(caseWorkerBinding.getPassword())) {
	                entity.setPassword(caseWorkerBinding.getPassword());
	            }
	            if (!entity.getAccStatus().equals(caseWorkerBinding.getAccStatus())) {
	                entity.setAccStatus(caseWorkerBinding.getAccStatus());
	            }
	            caseWorkerRepo.save(entity);
		         
			return "Updated Successfully";
		}).orElse("No user details Found");
		
	}

	private String readEmailBody(String fullname, String pwd, String filename) {
		String url = "";
//		Optional<UserMaster> byId = userMasterRepo.findById(userId);
//		// Check if the UserMaster is present
//		if (byId.isPresent()) {
//			UserMaster userMaster = byId.get();
//			logger.info("UserMaster found: " + userMaster); // Log the found user
//			// Generate the URL only if the user's email is verified
//			if (Boolean.FALSE.equals(userMaster.getIsVerifiedEmail())) {
//				url = "http://localhost:9393/api/verifyemail/" + userId;
//			}
//		}
		logger.info("Generated URL: " + url);

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
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {
			logger.error("Exception occured", e);
			;
		}
		return mailBody;
	}
}
