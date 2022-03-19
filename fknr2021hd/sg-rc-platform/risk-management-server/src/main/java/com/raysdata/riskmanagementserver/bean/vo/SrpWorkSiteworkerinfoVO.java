package com.raysdata.riskmanagementserver.bean.vo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * Copyright ©2003- 2020 State Grid Corporation of China,All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: 安全生产风险管控平台
 * @Version：V1.0
 * @Package: com.nariit.rmcp.core.vo.entry
 * @Author: chenchao
 * @Create: 2020/9/2 10:05
 * @Description: 作业人员基本信息VO
 * @History: modify
 **/
public class SrpWorkSiteworkerinfoVO extends BaseEntity {

    /**
     * 流程实例id
     */
    private String prciId;
    //    年度考试有效期内的最近一次成绩
    private Double score;
    //  年度考试有效期内的最近一次考试时间
    private String scoreTime;

    //    违章次数
    private Integer violaNum;
    //审批状态
    private Integer approveStatus;

    //  入职开始时间
    private Date begin;

    //    入职结束时间
    private Date end;

    private Integer minPoints;

    private Integer maxPoints;


    private String auditStatus;

    private  String negativelistpeopleId;


    private  String blacklistpeopleId;


    public String getBlacklistpeopleId() {
        return blacklistpeopleId;
    }

    public void setBlacklistpeopleId(String blacklistpeopleId) {
        this.blacklistpeopleId = blacklistpeopleId;
    }

    public String getNegativelistpeopleId() {
        return negativelistpeopleId;
    }

    public void setNegativelistpeopleId(String negativelistpeopleId) {
        this.negativelistpeopleId = negativelistpeopleId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    private List<String> workerNatures = new ArrayList<String>();

    //是否包含待准入
    private Boolean isContainEntry;

    private List<String> workinfoIds = new ArrayList<String>();

    /**
     * 身份证集合
     */
    private List<String> userCards = new ArrayList<String>();

    @PrimaryKey(tableName = "RMCP_AC_USER")
    private String siteworkerinfoId;

    /**
     * iscId
     */
    private String iscId;

    //	 关联ID
    private Integer id;
    //	 单位名称
    private String orgName;
    //	 单位ID
    private String orgId;

    /**
     * 本单位和所有下级单位集合
     */
    private List<String> orgIds;

    //	 部门/班组
    private String dep;
    //	 姓名
    private String name;

    /**
     * isc用户登录名
     */
    private String iscName;

    //	 年龄
    private String age;
    //	 人员照片
    private String photo;
    //	 性别:男，女
    private String sex;

    @ConvDict(aliasField = "sex")
    private String sexValue;

    //	 专业:输电、变电、配电、其他
    private String profession;

    @ConvDict(aliasField = "profession", cutFlag = ",")
    private String professionValue;
    //	 学历:小学、初中、高中、专科、本科、硕士研究生、博士研究生、其他
    private String education;

    @ConvDict(aliasField = "education")
    private String educationValue;
    //	 职称:正高工、高工、工程师、助理工程师
    private String title;

    @ConvDict(aliasField = "title")
    private String titleValue;
    //	 岗位:(字段详情参照初始化SQL)
    @ChangeLog(fieldName = "position", alias = "post")
    private String position;

    @ConvDict(aliasField = "position")
    private String positionValue;
    //	 紧急联系人
    private String emergencyContactpeople;
    //	 紧急联系人联系方式
    private String emergencyContactnumber;
    //	 联系方式
    private String contact;
    //	 身份证号
    private String idCard;
    //	 身份证正面照片
    private String idcardFphoto;
    //	 身份证反面照片
    private String idcardRphoto;
    //   保险资料图片
    private String insurance;
    //   体检资料图片
    private String bodyExamination;
    //	 入职日期
    private Date entryDate;
    //	 三种人标识:工作负责人、工作许可人、工作票签发人、非三种人
    private String threekindsIdentification;

    @ConvDict(aliasField = "threekindsIdentification", cutFlag = ",")
    private String threekindsIdentificationValue;
    //	 人员性质:主业人员、省管产业人员、外包人员
    private String workerNature;

    @ConvDict(aliasField = "workerNature")
    private String workerNatureValue;

    //用工性质
    private String employNature;

    @ConvDict(aliasField = "employNature")
    private String employNatureValue;

    //	 准入期限
    private Date accessPeriod;
    //	 准入状态:有效，无效
    private String accessState;

    @ConvDict(aliasField = "accessState")
    private String accessStateValue;
    //	 准入范围
    private String accessScope;
    //	 附件
    private String annex;
    //	 邮箱号
    private String email;
    //	 本年度违章积分
    private Integer violationPoints;
    //	 是否为黑名单:是，否
    private String isBlacklist;

    @ConvDict(aliasField = "isBlacklist")
    private String isBlacklistValue;
    //	 是否为负面清单:是，否
    private String isNeglist;

    @ConvDict(aliasField = "isNeglist")
    private String isNeglistValue;
    //   是否为关键人员
    private String isKeyWorker;
    // 状态修改标志
    private String statusFlag;
    // 退出类型标识
    private String exitFlag;

    @ConvDict(aliasField = "isKeyWorker")
    private String isKeyWorkerValue;
    // 信用代码
    private String creditCode;

    //
    private String isBlackFlag;

    /**
     * 发起人
     */
    private String sponsorId;

    /**
     * 发起人
     */
    private String sponsorName;

    /**
     * 准入执行状态
     */
    private String entryState;

    /**
     * 是否可报名
     */
    private String canSignUp;

    /**
     * 不可报名原因
     */
    private String reason;

    /**
     * 已报名人员id
     */
    private List<String> signUpList = new ArrayList<String>();

    private String currentUserId;

    /**
     * 准入/复核单位
     */
    private String reviewOrgName;


    /**
     * 审批状态的字典值
     */
    @ConvDict(aliasField = "approveStatus")
    private String approveStatusValue;

    /**
     * 父级组织ID
     */
    private String parentOrganizeId;

    /**
     * 本级组织ID
     */
    private String organizeId;

    /**
     * 本级组织类型
     */
    private String organizeType;

    /**
     * 入职状态
     */
    private String workingState;

    public String getWorkingState() {
        return workingState;
    }

    public void setWorkingState(String workingState) {
        this.workingState = workingState;
    }

    public Boolean getContainEntry() {
        return isContainEntry;
    }

    public void setContainEntry(Boolean containEntry) {
        isContainEntry = containEntry;
    }

    @Override
    public String getCurrentUserId() {
        return currentUserId;
    }

    @Override
    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getApproveStatusValue() {
        return approveStatusValue;
    }

    public void setApproveStatusValue(String approveStatusValue) {
        this.approveStatusValue = approveStatusValue;
    }

    public List<String> getSignUpList() {
        return signUpList;
    }

    public void setSignUpList(List<String> signUpList) {
        this.signUpList = signUpList;
    }

    public String getCanSignUp() {
        return canSignUp;
    }

    public void setCanSignUp(String canSignUp) {
        this.canSignUp = canSignUp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SrpWorkSiteworkerinfoVO() {
    }

    /**
     * 构造函数（违章扣减作业人员年度积分使用）
     *
     * @param siteworkerinfoId
     * @param violationPoints
     */
    public SrpWorkSiteworkerinfoVO(String siteworkerinfoId, Integer violationPoints) {
        this.siteworkerinfoId = siteworkerinfoId;
        this.violationPoints = violationPoints;
    }

    /**
     * 检查资质证书定时任务(处理人员使用)
     *
     * @param siteworkerinfoId
     * @param accessState
     */
    public SrpWorkSiteworkerinfoVO(String siteworkerinfoId, String accessState) {
        this.siteworkerinfoId = siteworkerinfoId;
        this.accessState = accessState;
    }

    public String getEmployNature() {
        return employNature;
    }

    public void setEmployNature(String employNature) {
        this.employNature = employNature;
    }

    public String getEmployNatureValue() {
        return employNatureValue;
    }

    public void setEmployNatureValue(String employNatureValue) {
        this.employNatureValue = employNatureValue;
    }

    public List<String> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<String> userCards) {
        this.userCards = userCards;
    }

    public String getPrciId() {
        return prciId;
    }

    public void setPrciId(String prciId) {
        this.prciId = prciId;
    }

    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public SrpWorkSiteworkerinfoVO(String siteworkerinfoId) {
        this.siteworkerinfoId = siteworkerinfoId;
    }

    public List<String> getWorkinfoIds() {
        return workinfoIds;
    }

    public void setWorkinfoIds(List<String> workinfoIds) {
        this.workinfoIds = workinfoIds;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getViolaNum() {
        return violaNum;
    }

    public void setViolaNum(Integer violaNum) {
        this.violaNum = violaNum;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(Integer minPoints) {
        this.minPoints = minPoints;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public List<String> getWorkerNatures() {
        return workerNatures;
    }

    public void setWorkerNatures(List<String> workerNatures) {
        this.workerNatures = workerNatures;
    }

    public String getAccessState() {
        return accessState;
    }

    public void setAccessState(String accessState) {
        this.accessState = accessState;
    }

    public String getSiteworkerinfoId() {
        return siteworkerinfoId;
    }

    public void setSiteworkerinfoId(String siteworkerinfoId) {
        this.siteworkerinfoId = siteworkerinfoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmergencyContactpeople() {
        return emergencyContactpeople;
    }

    public void setEmergencyContactpeople(String emergencyContactpeople) {
        this.emergencyContactpeople = emergencyContactpeople;
    }

    public String getEmergencyContactnumber() {
        return emergencyContactnumber;
    }

    public void setEmergencyContactnumber(String emergencyContactnumber) {
        this.emergencyContactnumber = emergencyContactnumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdcardFphoto() {
        return idcardFphoto;
    }

    public void setIdcardFphoto(String idcardFphoto) {
        this.idcardFphoto = idcardFphoto;
    }

    public String getIdcardRphoto() {
        return idcardRphoto;
    }

    public void setIdcardRphoto(String idcardRphoto) {
        this.idcardRphoto = idcardRphoto;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getThreekindsIdentification() {
        return threekindsIdentification;
    }

    public void setThreekindsIdentification(String threekindsIdentification) {
        this.threekindsIdentification = threekindsIdentification;
    }

    public String getWorkerNature() {
        return workerNature;
    }

    public void setWorkerNature(String workerNature) {
        this.workerNature = workerNature;
    }

    public Date getAccessPeriod() {
        return accessPeriod;
    }

    public void setAccessPeriod(Date accessPeriod) {
        this.accessPeriod = accessPeriod;
    }

    public String getAccessScope() {
        return accessScope;
    }

    public void setAccessScope(String accessScope) {
        this.accessScope = accessScope;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getViolationPoints() {
        return violationPoints;
    }

    public void setViolationPoints(Integer violationPoints) {
        this.violationPoints = violationPoints;
    }

    public String getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(String isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public String getIsNeglist() {
        return isNeglist;
    }

    public void setIsNeglist(String isNeglist) {
        this.isNeglist = isNeglist;
    }

    public String getIsKeyWorker() {
        return isKeyWorker;
    }

    public void setIsKeyWorker(String isKeyWorker) {
        this.isKeyWorker = isKeyWorker;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public String getProfessionValue() {
        return professionValue;
    }

    public void setProfessionValue(String professionValue) {
        this.professionValue = professionValue;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(String positionValue) {
        this.positionValue = positionValue;
    }

    public String getThreekindsIdentificationValue() {
        return threekindsIdentificationValue;
    }

    public void setThreekindsIdentificationValue(String threekindsIdentificationValue) {
        this.threekindsIdentificationValue = threekindsIdentificationValue;
    }

    public String getWorkerNatureValue() {
        return workerNatureValue;
    }

    public void setWorkerNatureValue(String workerNatureValue) {
        this.workerNatureValue = workerNatureValue;
    }

    public String getAccessStateValue() {
        return accessStateValue;
    }

    public void setAccessStateValue(String accessStateValue) {
        this.accessStateValue = accessStateValue;
    }

    public String getIsBlacklistValue() {
        return isBlacklistValue;
    }

    public void setIsBlacklistValue(String isBlacklistValue) {
        this.isBlacklistValue = isBlacklistValue;
    }

    public String getIsNeglistValue() {
        return isNeglistValue;
    }

    public void setIsNeglistValue(String isNeglistValue) {
        this.isNeglistValue = isNeglistValue;
    }

    public String getIsKeyWorkerValue() {
        return isKeyWorkerValue;
    }

    public void setIsKeyWorkerValue(String isKeyWorkerValue) {
        this.isKeyWorkerValue = isKeyWorkerValue;
    }

    public String getExitFlag() {
        return exitFlag;
    }

    public void setExitFlag(String exitFlag) {
        this.exitFlag = exitFlag;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getIsBlackFlag() {
        return isBlackFlag;
    }

    public void setIsBlackFlag(String isBlackFlag) {
        this.isBlackFlag = isBlackFlag;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getEntryState() {
        return entryState;
    }

    public void setEntryState(String entryState) {
        this.entryState = entryState;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getIscId() {
        return iscId;
    }

    public void setIscId(String iscId) {
        this.iscId = iscId;
    }

    public String getIscName() {
        return iscName;
    }

    public void setIscName(String iscName) {
        this.iscName = iscName;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getBodyExamination() {
        return bodyExamination;
    }

    public void setBodyExamination(String bodyExamination) {
        this.bodyExamination = bodyExamination;
    }

    public String getReviewOrgName() {
        return reviewOrgName;
    }

    public void setReviewOrgName(String reviewOrgName) {
        this.reviewOrgName = reviewOrgName;
    }

    public String getParentOrganizeId() {
        return parentOrganizeId;
    }

    public void setParentOrganizeId(String parentOrganizeId) {
        this.parentOrganizeId = parentOrganizeId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeType() {
        return organizeType;
    }

    public void setOrganizeType(String organizeType) {
        this.organizeType = organizeType;
    }
}
