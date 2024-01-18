package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.CollegeNotFoundException;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import com.vagner.springboot.department.project.utils.CollegeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CollegeService
{
    @Autowired
    private CollegeRepositoryInterface collegeRepository;

    public College saveCollege(College college) throws DuplicateDepartmentException, DuplicateMajorException
    {
        //ensure that all the departments have unique department code. Other validation can be added if needed.
        CollegeUtils.validateDepartmentCodes(college);
        //ensure that college major list has non repeated values. (Math == math) = true
        CollegeUtils.validateMajorList(college);
        return collegeRepository.save(college);
    }

    public List<College> fetchCollegeList()
    {
        List<College> list = collegeRepository.findAll();
        System.out.println(list);
        return list;
    }

    public ResponseEntity<String> deleteCollegeById(Long collegeId) throws CollegeNotFoundException {
        boolean opt = collegeRepository.existsById(collegeId);
        if (opt) {
            collegeRepository.deleteById(collegeId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.status(200).headers(headers).body("{ \"Deletion\" : \"Successful for College with id " + collegeId + "\"}");
        }
        throw new CollegeNotFoundException("College Not Found, cannot delete.");
    }

    public College updateCollege(College updatedCollege, long collegeId) throws CollegeNotFoundException, DuplicateMajorException {
        if(!collegeRepository.existsById(collegeId))
            throw new CollegeNotFoundException("Cannot PUT as collegeId does not exist.");

        College collegeFromDatabase = collegeRepository.getById(collegeId);

        if(Objects.nonNull(updatedCollege.getCollegeName()) && !updatedCollege.getCollegeName().isEmpty())
            collegeFromDatabase.setCollegeName(updatedCollege.getCollegeName());
        if(Objects.nonNull(updatedCollege.getPhone()) && !updatedCollege.getPhone().isEmpty())
            collegeFromDatabase.setPhone(updatedCollege.getPhone());
        if(Objects.nonNull(updatedCollege.getPresident()) && !updatedCollege.getPresident().isEmpty())
            collegeFromDatabase.setPresident(updatedCollege.getPresident());
        if(Objects.nonNull(updatedCollege.getMajors()) && !updatedCollege.getMajors().isEmpty())
        {   //aux me
            CollegeUtils.validateMajorList(updatedCollege);
            collegeFromDatabase.setMajors(updatedCollege.getMajors());
        }

        // NOTE: list of departments. Should be done via the department PUT

        //TODO: collegeAddress validation and posting

        return collegeRepository.save(collegeFromDatabase);
    }
}
