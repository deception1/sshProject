package com.shop.www.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.shop.www.dao.Dao;
import com.shop.www.util.Paging;

public class DaoImpl implements Dao {
	private SessionFactory sessionFactory=null;
	public static final String C="C";
	public static final String R="R";
	public static final String U="U";
	public static final String D="D";
	public static final String Q="Q";
	public static final String E="E";
	
	@SuppressWarnings("unchecked")
	public List<Object> GetObjectsDataByHQL(String hql, Object entity,Paging p) {
		List<Object> list=new ArrayList<Object>();
		Session s=null;
		try {
			s = sessionFactory.openSession();
			Query query = s.createQuery(hql);
			if (entity != null) {
				if(hql.contains("?")){
					if(entity instanceof Object[]){
						Object[] params=(Object[])entity;
						for(int i=0;i<params.length;i++) query.setParameter(i, params[i]);
					}
				}else query.setProperties(entity);
			}
			if(p!=null){
				query.setFirstResult((p.getPageIndex()-1)*p.getPageSize());
				query.setMaxResults(p.getPageSize());
			}
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return list;
	}

	public List<Object> GetObjectsDataByPaging(String hql, Object entity,Paging p) {
		p.setCount(GetCount(hql, entity));
		return GetObjectsDataByHQL(hql, entity, p);
	}

	public long GetCount(String hql, Object entity) {
		String tempHQL=hql.substring(hql.indexOf("from"));
		String newHQL="select count(*) "+tempHQL;
		return (Long)this.GetObjectsDataByHQL(newHQL, entity, null).get(0);
	}

	public boolean ADU(Object entity,String sign,Object...deleteHQL) {
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		try {
			s.clear();
			if (C.equals(sign)) s.save(entity);
			else if (D.equals(sign)){
				if(deleteHQL!=null){
					List<Object> list=GetObjectsDataByHQL(deleteHQL[0].toString(),entity,null);
					for(int i=0;i<list.size();i++){
						s.delete(list.get(i));
					}
				}
			}
			else if (U.equals(sign)) s.update(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}finally{
			s.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> GetObjectsDataBySQL(String sql,Object...params) {
		List<Object> list=new ArrayList<Object>();
		Session s=null;
		try {
			s = sessionFactory.openSession();
			SQLQuery query = s.createSQLQuery(sql);
			if(params!=null&&params.length>0){
				for(int i=0;i<params.length;i++){
					query.setParameter(i, params[i]);
				}
			}
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return list;
	}
	
	public int ExcuteBySQL(String sql,Object...params){
		int result=0;
		Session s=null;
		try {
			s = sessionFactory.openSession();
			SQLQuery query = s.createSQLQuery(sql);
			if(params!=null&&params.length>0){
				for(int i=0;i<params.length;i++){
					query.setParameter(i, params[i]);
				}
			}
			result=query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return result;
	}
	
	public List<Object> CRUD(String sign, String shql,Object...params){
		List<Object> list=new ArrayList<Object>();
		if(Q.equals(sign)){
			list=GetObjectsDataBySQL(shql,params);
		}else if(E.equals(sign)){
			list.add(ExcuteBySQL(shql,params));
		}else{
			Object entity=null;
			Paging p=null;
			if(params!=null){
				if(params.length==1) if(params[0] instanceof Paging) p=(Paging)params[0]; else entity=params[0];
				if(params.length==2){
					if(params[0] instanceof Paging){
						p=(Paging)params[0];
						entity=params[1];
					}else{
						entity=params[0];
						p=(Paging)params[1];
					}
				}
			}
			list=CRUD(sign, shql, entity, p);
		}
		return list;
	}
	
	public List<Object> CRUD(String sign, String hql, Object entity, Paging p) {
		List<Object> list=new ArrayList<Object>();
		if(R.equals(sign)){
			if(p!=null) list=GetObjectsDataByPaging(hql, entity, p);
			else list=GetObjectsDataByHQL(hql, entity, null);
		}else if(C.equals(sign)){
			list.add(ADU(entity,C));
		}else if(U.equals(sign)){
			list.add(ADU(entity,U));
		}else if(D.equals(sign)){
			list.add(ADU(entity,D,hql));
		}
		return list;
	}

	public List<List<Object>> CRUD(List<List<String>> sign, List<List<Object>> obj) {
		List<List<Object>> lists=new ArrayList<List<Object>>();
		List<Object> list=new ArrayList<Object>();
		List<Object> cons=new ArrayList<Object>();
		boolean con=false;
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		try {
			for(int i=0;i<sign.size();i++){
				if(R.equals(sign.get(i).get(0))){
					if(obj.get(i).get(1)!=null) list=GetObjectsDataByPaging(sign.get(i).get(1), obj.get(i).get(0), (Paging)obj.get(i).get(1));
					else list=GetObjectsDataByHQL(sign.get(i).get(1), obj.get(i).get(0), null);
				}else{
					for(int j=0;j<obj.get(i).size();j++){
						if (C.equals(sign.get(i).get(0))) s.save(obj.get(i).get(j));
						else if (U.equals(sign.get(i).get(0))) s.update(obj.get(i).get(j));
						else if (D.equals(sign.get(i).get(0))){
							if(sign.get(i).get(1)!=""&&sign.get(i).get(1)!=null){
								List<Object> templist=GetObjectsDataByHQL(sign.get(i).get(1),obj.get(i).get(j),null);
								for(int z=0;z<templist.size();z++){
									s.delete(templist.get(z));
								}
							}
						}
					}
				}
			}
			tx.commit();
			con=true;
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			con=false;
		}finally{
			s.close();
		}
		cons.add(con);
		lists.add(0,list);
		lists.add(1,cons);
		return lists;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
