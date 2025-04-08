package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class SpecialQuestionList {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long gender;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long birthday;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long phone;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long email;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long education;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long major;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long occupation;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mbti;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long availablePeriod;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preferredActivityField;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hobby;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sns;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long motivation;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long activityExperience;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skill;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aspiration;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long availableTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long referralSource;

}
