package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IInsuranceMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.IPayTheWayMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 * 缴纳规则
 * @author lzh
 * @create 2017/9/27 14:19
 */
@Service
public class PayTheWayService {

    @Resource
    private IPayTheWayMapper payTheWayMapper;
    @Resource
    private IInsuranceMapper insuranceMapper;
    @Resource
    private ILogMapper logMapper;

    /**
     * 计算该档次下 最低基数  和 最高基数
     * @param levelId
     * @return
     */
    public Map<String,Object> countPayTheWay(Integer levelId){
        Map<String,Object> result = new HashMap<String, Object>();
        Double min = null;
        Double max = null;
        List<PayTheWay> payTheWays = payTheWayMapper.queryPayTheWayByLevel(levelId);
        if(null != payTheWays && payTheWays.size() > 0){
            List<Double> minList = new ArrayList<Double>();
            List<Double> maxList = new ArrayList<Double>();
            for (PayTheWay payTheWay : payTheWays) {
                if(null != payTheWay.getMeMinScope()){
                    minList.add(payTheWay.getMeMinScope());
                }
                if(null != payTheWay.getMeMaxScope()){
                    maxList.add(payTheWay.getMeMaxScope());
                }
            }
            if(minList.size() > 0){
                Collections.sort(minList);
                min = minList.get(0);
            }
            if(maxList.size() > 0){
                Collections.sort(maxList);
                max = maxList.get(maxList.size() - 1);
            }
        }
        result.put("min",min);
        result.put("max",max);
        return result;
    }

    /**
     * 新增
     * @param payTheWay
     */
    @Transactional
    public void save(PayTheWay payTheWay,User user) {
        payTheWayMapper.save(payTheWay);
        Insurance insurance = insuranceMapper.queryInsuranceById(payTheWay.getInsuranceId());
        if(null != insurance){
            logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_CONFIG,"新增险种缴纳规则"+insurance.getInsuranceName(),StatusConstant.LOG_FLAG_ADD));
        }
    }

    /**
     * 更新不为空字段
     * @param payTheWay
     */
    @Transactional
    public void update(PayTheWay payTheWay,User user) {
        PayTheWay info = payTheWayMapper.info(payTheWay.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"对象为空");
        }
        payTheWayMapper.update(payTheWay);
        Insurance insurance = insuranceMapper.queryInsuranceById(info.getInsuranceId());
        if(null != insurance){
            StringBuffer sb = new StringBuffer("修改"+insurance.getInsuranceName()+"的缴纳规则");
            if(!CommonUtil.isEmpty(payTheWay.getCoPayType())){
                // 公司缴纳类型 0：金额  1：比例   2：跟随办理方
                if(payTheWay.getCoPayType() == 0){
                    sb.append(" 公司缴纳类型: 金额");
                }
                else if(payTheWay.getCoPayType() == 1){
                    sb.append(" 公司缴纳类型: 比例");
                }
                else if(payTheWay.getCoPayType() == 2){
                    sb.append(" 公司缴纳类型: 跟随办理方");
                }
            }
            if(null != payTheWay.getCoPayPrice()){
                if(payTheWay.getCoPayType() == 0){
                    sb.append(" 公司缴纳金额: "+payTheWay.getCoPayPrice());
                }
                else if(payTheWay.getCoPayType() == 1){
                    sb.append(" 公司缴纳比例: "+payTheWay.getCoPayPrice());
                }
            }
            if(null != payTheWay.getCoComputationalAccuracy()){
                sb.append(" 公司计算精度: "+ payTheWay.getCoComputationalAccuracy());
            }
            if(null != payTheWay.getCoComputationRule()){
                // 公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                if(payTheWay.getCoComputationRule() == 0){
                    sb.append(" 公司计算规则：四舍五入");
                }
                else if(payTheWay.getCoComputationRule() == 1){
                    sb.append(" 公司计算规则：升角省分");
                }
                else if(payTheWay.getCoComputationRule() == 2){
                    sb.append(" 公司计算规则：去尾");
                }
                else if(payTheWay.getCoComputationRule() == 3){
                    sb.append(" 公司计算规则：进一");
                }
            }
            if(null != payTheWay.getIsCMakeASupplementaryPayment()){
                sb.append(" 公司是否补缴：" + (0 == payTheWay.getIsCMakeASupplementaryPayment() ? "否" : "是"));
            }
            if(null != payTheWay.getIsCDimissionSupplementaryPay()){
                sb.append(" 公司是否离职补差：" + (0 == payTheWay.getIsCDimissionSupplementaryPay() ? "否" : "是"));
            }
            if(null != payTheWay.getCoPrecision()){
                sb.append(" 公司填写精度 :" + payTheWay.getCoPrecision());
            }

            if(null != payTheWay.getMePayType()){
                // 个人缴纳类型 0：金额  1：比例   2：跟随办理方
                if(payTheWay.getMePayType() == 0){
                    sb.append(" 个人缴纳类型：金额");
                }
                else if(payTheWay.getMePayType() == 1){
                    sb.append(" 个人缴纳类型：比例");
                }
                else if(payTheWay.getMePayType() == 2){
                    sb.append(" 个人缴纳类型：跟随办理方");
                }
            }
            if(null != payTheWay.getMePayPrice()){
                if(payTheWay.getMePayType() == 0){
                    sb.append(" 个人缴纳金额："+payTheWay.getMePayPrice());
                }
                else if(payTheWay.getMePayType() == 1){
                    sb.append(" 个人缴纳比例："+payTheWay.getMePayPrice());
                }
            }
            if(null != payTheWay.getMeComputationalAccuracy()){
                sb.append(" 个人计算精度: "+payTheWay.getMeComputationalAccuracy());
            }
            if(null != payTheWay.getMeComputationRule()){
                // 个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                if(0 == payTheWay.getMeComputationRule()){
                    sb.append(" 个人计算规则：四舍五入");
                }
                else if(1 == payTheWay.getMeComputationRule()){
                    sb.append(" 个人计算规则：升角省分");
                }
                else if(2 == payTheWay.getMeComputationRule()){
                    sb.append(" 个人计算规则：去尾");
                }
                else if(3 == payTheWay.getMeComputationRule()){
                    sb.append(" 个人计算规则：进一");
                }
            }
            if(null != payTheWay.getIsMMakeASupplementaryPayment()){
                sb.append(" 个人是否补缴：" + (0 == payTheWay.getIsMMakeASupplementaryPayment() ? "否" : "是"));
            }

            if(null != payTheWay.getIsMDimissionSupplementaryPay()){
                sb.append(" 个人是否离职补差：" + (0 == payTheWay.getIsMDimissionSupplementaryPay() ? "否" : "是"));
            }
            if(null != payTheWay.getMePrecision()){
                sb.append(" 个人填写精度：" + payTheWay.getMePrecision());
            }
            if(null != payTheWay.getCoMinScope()){
                sb.append(" 公司基数最小范围：" + payTheWay.getCoMinScope());
            }
            if(null != payTheWay.getCoMaxScope()){
                sb.append(" 公司基数最大范围：" + payTheWay.getCoMaxScope());
            }
            if(null != payTheWay.getMeMinScope()){
                sb.append(" 个人基数最小范围：" + payTheWay.getMeMinScope());
            }
            if(null != payTheWay.getMeMaxScope()){
                sb.append(" 个人基数最大范围：" + payTheWay.getMeMaxScope());
            }
            logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_CONFIG,sb.toString(),StatusConstant.LOG_FLAG_UPDATE));
        }
    }

    /**
     * 更新所有字段
     * @param payTheWay
     */
    public void updateAll(PayTheWay payTheWay) {
        payTheWayMapper.updateAll(payTheWay);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public PayTheWay info(Integer id) {
        return payTheWayMapper.info(id);
    }


    /**
     * 根据险种id 获取上一次最新添加的数据
     * @param insuranceId
     * @return
     */
    public PayTheWay getNewByInsuranceId(Integer insuranceId) {
        return payTheWayMapper.getNewByInsuranceId(insuranceId);
    }


    /**
     * 后台页面 分页获取缴纳规则
     * @param pageArgs 分页属性
     * @param coComputationRule 公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
     * @param isCMakeASupplementaryPayment 公司是否补缴  0：否  1：是
     * @param meComputationRule 个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
     * @param isMMakeASupplementaryPayment 个人是否补缴  0：否  1：是
     * @param insuranceId 险种ID
     * @param isValid  是否有效 0 无效 1有效
     * @return
     */
    public PageList<PayTheWay> list(PageArgs pageArgs , Integer coComputationRule ,
                                    Integer isCMakeASupplementaryPayment,
                                    Integer meComputationRule , Integer isMMakeASupplementaryPayment,
                                    Integer insuranceId, Integer isValid) {
        PageList<PayTheWay> pageList = new PageList<PayTheWay>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("coComputationRule", coComputationRule);
        map.put("isCMakeASupplementaryPayment", isCMakeASupplementaryPayment);
        map.put("meComputationRule", meComputationRule);
        map.put("isMMakeASupplementaryPayment", isMMakeASupplementaryPayment);
        map.put("insuranceId", insuranceId);
        map.put("isValid", isValid);
        int count = payTheWayMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(payTheWayMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

}
