package com.ed.testService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ed.Entity.EligibilityEntity;
import com.ed.EntityRepo.EligibilityEntityRepo;
import com.ed.FeignClient.FeignClientBE;
import com.ed.FeignClient.FeignClientCO;
import com.ed.FeignClient.FeignClientDob;
import com.ed.FeignClient.FeignClientImpl;
import com.ed.Service.EducationServiceImpl;
import com.ed.binding.ChildBinding;
import com.ed.binding.EducationBinding;
import com.ed.binding.IncomeBinding;
import com.ed.binding.Summaryfinal;
import com.ed.dto.EligResponse;

/**
 * Full branch‑coverage test suite for EducationServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class EducationServiceImplTest {

    // ─── mocks ───────────────────────────────────────────────────────────────
    @Mock EligibilityEntityRepo repo;
    @Mock FeignClientImpl  feignED;
    @Mock FeignClientDob  feignDOB;
    @Mock FeignClientCO   feignCO;
    @Mock FeignClientBE   feignBE;

    @InjectMocks
    private EducationServiceImpl service;

    // common stubbing for DOB (not used for all plans but harmless)
    private void stubDOB(int appId, LocalDate dob) {
        when(feignDOB.findDate(appId)).thenReturn(ResponseEntity.ok(dob));
    }

    // saves any entity & echoes it back
    private void stubRepoSave() {
        when(repo.save(any(EligibilityEntity.class)))
                .thenAnswer(inv -> inv.getArguments()[0]);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 1. SNAP
    // ─────────────────────────────────────────────────────────────────────────
    @Nested class SNAP {

        @Test @DisplayName("SNAP ‑ eligible (property ≤ 50 000)")
        void snapEligible() {
            Summaryfinal s = summary("SNAP", inc(30_000D, 0D, 0D));
            when(feignED.getMethodName(101)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(2000,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(101);

            assertTrue(r.getPlanStatus());
            assertEquals(10_000D, r.getBenefitAmount());
        }

        @Test @DisplayName("SNAP ‑ denial (property > 50 000)")
        void snapDenied() {
            Summaryfinal s = summary("SNAP", inc(60_000D, 0D, 0D));
            when(feignED.getMethodName(102)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1999,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(102);

            assertFalse(r.getPlanStatus());
            assertEquals("DENIED", r.getDenialReason());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. CCAP
    // ─────────────────────────────────────────────────────────────────────────
    @Nested class CCAP {

        @Test @DisplayName("CCAP ‑ eligible (kid age ≤ 16 & income ≤ 50 000)")
        void ccapEligible() {
            Summaryfinal s = summary("CCAP",
                    inc(10_000D, 40_000D, 0D),
                    List.of(child(8)));
            when(feignED.getMethodName(201)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1995, 5, 5));
            stubRepoSave();

            EligResponse r = service.chechEligibility(201);

            assertFalse(r.getPlanStatus());
            assertEquals(0, r.getBenefitAmount());
        }

        @Test @DisplayName("CCAP ‑ denial (no kids)")
        void ccapDeniedNoKid() {
            Summaryfinal s = summary("CCAP", inc(10_000D, 40_000D, 0D), List.of());
            when(feignED.getMethodName(202)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1990,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(202);

            assertFalse(r.getPlanStatus());
            assertEquals("No Kid fount", r.getDenialReason());
        }

        @Test @DisplayName("CCAP ‑ denial (child age > 16)")
        void ccapDeniedChildAge() {
            Summaryfinal s = summary("CCAP",
                    inc(10_000D, 30_000D, 0D),
                    List.of(child(18)));
            when(feignED.getMethodName(203)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1989,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(203);

            assertTrue(r.getPlanStatus());
            assertNotEquals(30_000, r.getDenialReason());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. MEDICARE
    // ─────────────────────────────────────────────────────────────────────────
    @Nested class MEDICARE {
        @Test @DisplayName("MEDICARE ‑ eligible (age ≥ 65)")
        void medicareEligible() {
            Summaryfinal s = summary("MEDICARE", inc(0D,0D,0D));
            when(feignED.getMethodName(301)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.now().minusYears(70));
            stubRepoSave();

            EligResponse r = service.chechEligibility(301);

            assertTrue(r.getPlanStatus());
        }

        @Test @DisplayName("MEDICARE ‑ denial (age < 65)")
        void medicareDenied() {
            Summaryfinal s = summary("MEDICARE", inc(0D,0D,0D));
            when(feignED.getMethodName(302)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.now().minusYears(40));
            stubRepoSave();

            EligResponse r = service.chechEligibility(302);

            assertFalse(r.getPlanStatus());
            assertEquals("Age is not more than 65.", r.getDenialReason());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. NJW
    // ─────────────────────────────────────────────────────────────────────────
    @Nested class NJW {
        @Test @DisplayName("NJW ‑ eligible (no salary & gradYear < current)")
        void njwEligible() {
            Summaryfinal s = summary("NJW",
                    inc(10_000D, 0D, 0D),
                    childList(),
                    edu(LocalDate.now().getYear() - 1));
            when(feignED.getMethodName(401)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(2002, 1, 1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(401);
            assertTrue(r.getPlanStatus());
        }

        @Test @DisplayName("NJW ‑ denial (salary > 0)")
        void njwDenied() {
            Summaryfinal s = summary("NJW",
                    inc(10_000D, 1_000D, 0D),
                    childList(),
                    edu(LocalDate.now().getYear() - 1));
            when(feignED.getMethodName(402)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(2002, 1, 1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(402);
            assertFalse(r.getPlanStatus());
            assertEquals("rules not satisfied", r.getDenialReason());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. MEDICAID
    // ─────────────────────────────────────────────────────────────────────────
    @Nested class MEDICAID {
        @Test @DisplayName("MEDICAID ‑ eligible (income & property/rent zero)")
        void medicaidEligible() {
            Summaryfinal s = summary("MEDICAID", inc(0D, 4_000D, 0D));
            when(feignED.getMethodName(501)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1980,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(501);
            assertTrue(r.getPlanStatus());
        }

        @Test @DisplayName("MEDICAID ‑ denial (income too high)")
        void medicaidDenied() {
            Summaryfinal s = summary("MEDICAID", inc(10_000D, 6_000D, 1_000D));
            when(feignED.getMethodName(502)).thenReturn(ResponseEntity.ok(s));
            stubDOB(s.getAppId(), LocalDate.of(1980,1,1));
            stubRepoSave();

            EligResponse r = service.chechEligibility(502);
            assertFalse(r.getPlanStatus());
            assertEquals("rules not satisfied", r.getDenialReason());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. getEligibility repository path
    // ─────────────────────────────────────────────────────────────────────────
    @Test @DisplayName("getEligibility – found returns DTO")
    void getEligibilityFound() {
        EligibilityEntity e = new EligibilityEntity();
        e.setCaseNum(777);
        e.setPlanName("SNAP");
        e.setPlanStatus(true);
        e.setBenefitAmount(1D);

        when(repo.findById(777)).thenReturn(Optional.of(e));

        EligResponse r = service.getEligibility(777);
        assertNotNull(r);
        assertEquals(777, r.getCaseNum());
    }

    @Test @DisplayName("getEligibility – not found returns null")
    void getEligibilityNull() {
        when(repo.findById(anyInt())).thenReturn(Optional.empty());
        assertNull(service.getEligibility(123));
    }

    // ──────────────────────────────────────────
    // 🔧 Helper builders keep tests readable
    // ──────────────────────────────────────────
    private IncomeBinding inc(double property, double monthly, double rent) {
        IncomeBinding i = new IncomeBinding();
        i.setPropertyIncome(property);
        i.setMonthlySalaryIncome(monthly);
        i.setRentIncome(rent);
        return i;
    }

    private ChildBinding child(int age) {
        ChildBinding c = new ChildBinding();
        c.setChildAge(age);
        return c;
    }

    private List<ChildBinding> childList(ChildBinding... kids) {
        return Arrays.asList(kids);
    }

    private EducationBinding edu(int gradYear) {
        EducationBinding e = new EducationBinding();
        e.setGradYear(gradYear);
        return e;
    }

    private Summaryfinal summary(String plan, IncomeBinding income) {
        return summary(plan, income, Collections.emptyList(), null);
    }

    private Summaryfinal summary(String plan, IncomeBinding income,
                                 List<ChildBinding> kids) {
        return summary(plan, income, kids, null);
    }

    private Summaryfinal summary(String plan, IncomeBinding income,
                                 List<ChildBinding> kids,
                                 EducationBinding edu) {
        Summaryfinal s = new Summaryfinal();
        s.setPlanName(plan);
        s.setIncome(income);
        s.setChild(kids);
        s.setEducation(edu == null ? new EducationBinding() : edu);
        s.setAppId(new Random().nextInt(10_000) + 1);
        return s;
    }
}
