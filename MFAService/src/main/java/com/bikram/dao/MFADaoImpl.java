package com.bikram.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bikram.bean.MFABean;
import com.bikram.util.HibernateUtils;

@Repository
@Component
public class MFADaoImpl implements MFADao {

	@Override
	public void insertOrUdateMFA(String email, String companyName, String qrPath,String code,String secretKey,String fileName) {
		
		System.out.println("Email "+email);
		System.out.println("Company Name :"+companyName);
		System.out.println("qrPath "+qrPath);
		Session session=HibernateUtils.getNewHibernateSession();
		MFABean bean=new MFABean();
		bean.setEmail(email);
		bean.setCompanyName(companyName);
		bean.setQrcodePath(qrPath);
		bean.setDate(new Date());
		bean.setCode(code);
		bean.setSecretKey(secretKey);
		bean.setFileName(fileName);
		MFABean mfaBean=(MFABean) session.get(MFABean.class, email);
		Transaction tx=session.beginTransaction();
		if(mfaBean!=null) {
			mfaBean.setCompanyName(companyName);
			mfaBean.setQrcodePath(qrPath);
			mfaBean.setDate(new Date());
			mfaBean.setCode(code);
			mfaBean.setSecretKey(secretKey);
			//mfaBean.setFileName(fileName);
			session.update(mfaBean);
		}
		else {
			session.save(bean);
		}
		tx.commit();
		session.flush();
		session.close();
			
	}
	@Override
	public List<MFABean> getAllMFA(){
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria=session.createCriteria(MFABean.class);
		List<MFABean> list=criteria.list();
		return list;
	}
	
	public String validateMFA(String email,String code) {
		Session session=HibernateUtils.getNewHibernateSession();
		Criteria criteria=session.createCriteria(MFABean.class);
		criteria.add(Restrictions.and(Restrictions.eq("email", email),Restrictions.eq("code", code)));
		if(criteria.list().size()>0)
			return "SUCCESS";
		else
			return "FAIL";
	}

}
