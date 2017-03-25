package com.shop.www.dao;

import java.util.List;
import com.shop.www.util.Paging;

public interface Dao {
	/**
	 * 通用的查询数据的方法，条件为通过HQL语句，可对查询进行分页
	 * @param hql
	 * <pre>
	 * HQL语句
	 * </pre>
	 * @param entity
	 * <pre>
	 * HQL语句需要参数既需条件的时候所传进来的实体类，HQL语句有哪些参数，实体类就必须赋哪些值
	 * </pre>
	 * @param p
	 * <pre>
	 * 用来实现分页的实体类，若不为null则进行分页计算，其中需指定初始值的属性为pageSize（页面数据行数）
	 * </pre>
	 * @return
	 * <pre>
	 * 返回值为List<Object>的泛型集合
	 * </pre>
	 */
	List<Object> GetObjectsDataByHQL(String hql,Object entity,Paging p);
	/**
	 * 通用的分页计算函数，可对需进行分页查询数据进行计算
	 * @param 
	 * <pre>
	 * hql HQL语句
	 * </pre>
	 * @param entity 
	 * <pre>
	 * HQL语句需要参数既需条件的时候所传进来的实体类，HQL语句有哪些参数，实体类就必须赋哪些值
	 * </pre>
	 * @param p 
	 * <pre>
	 * 用来实现分页的实体类，若不为null则进行分页计算，其中需指定初始值的属性为pageSize（页面数据行数）
	 * </pre>
	 * @return
	 * <pre>
	 * 返回值为List<Object>的泛型集合
	 * </pre>
	 */
	List<Object> GetObjectsDataByPaging(String hql,Object entity,Paging p);
	/**
	 * 计算数据条总数的方法，统计数据库里有多少条指定条件查询的数据
	 * @param hql
	 * <pre>
	 * HQL语句
	 * </pre>
	 * @param entity
	 * <pre>
	 * HQL语句需要参数既需条件的时候所传进来的实体类，HQL语句有哪些参数，实体类就必须赋哪些值
	 * </pre>
	 * @return
	 * <pre>
	 * 返回值为long类型的数据条总数
	 * </pre>
	 */
	long GetCount(String hql,Object entity);
	/**
	 * 通用的ADU（增、删、改）方法
	 * @param entity 
	 * <pre>
	 * 需要进行操作的指定实体类，比如删除某条数据对应程序这边的某个实体类，实体类必须含有实质的数据
	 * </pre>
	 * @param sign 
	 * <pre>
	 * 标识此次操作为C或U或D，只能有这三个选项
	 * </pre>
	 * @param deleteHQL 
	 * <pre>
	 * 用来做删除数据的HQL语句，不是必须加的参数，删除数据操作时必须加。因为我们删除数据一般只是指定这条数据的ID，
	 * 而Hibernate中删除数据需要完整的数据才可进行删除操作，所以有必要传入一条指定删除数据ID的HQL语句进行查询获取完整的数据
	 * </pre>
	 * @return 
	 * 
	 * 返回值为boolean类型，若为true则操作成功，false失败
	 */
	boolean ADU(Object entity,String sign,Object...deleteHQL);
	/**
	 * 查询数据集，通过原生SQL
	 * @param sql
	 * <pre>
	 * SQL语句
	 * </pre>
	 * @return
	 * <pre>
	 * @param params
	 * <pre>
	 * SQL语句参数，可选，可多个
	 * </pre>
	 * 返回数据集合
	 * </pre>
	 */
	List<Object> GetObjectsDataBySQL(String sql,Object...params);
	/**
	 * 执行原生SQL
	 * @param sql
	 * <pre>
	 * SQL语句
	 * </pre>
	 * @return
	 * <pre>
	 * @param params
	 * <pre>
	 * SQL语句参数，可选，可多个
	 * </pre>
	 * 返回值为int类型，若为大于零则执行成功，否则失败
	 * </pre>
	 */
	int ExcuteBySQL(String sql,Object...params);
	/**
	 * 执行原生SQL 查询（Query）、执行（Excute）或执行HQL语句CRUD 增加（Create）、读取（Retrieve）（重新得到数据）、更新（Update）和删除（Delete）
	 * @param sign
	 * <pre>
	 * 标识此次操作为C、R、U、D、Q、E，只能有这六个选项
	 * </pre>
	 * @param sql
	 * <pre>
	 * SQL语句，或HQL语句
	 * </pre>
	 * @param params
	 * <pre>
	 * SQL语句或HQL语句参数，可选，可多个，HQL语句时最多可两个（实体对象{若为？号赋参，则需用Object[]指定参数值}，分页对象），顺序无关
	 * </pre>
	 * @return
	 * <pre>
	 * 返回值为List<Object>类型，若为查询操作（R、Q）则返回数据集合，若为执行操作（E）则返回受影响行数，若为大于零则执行成功，否则失败，若为执行操作（C、U、D）则返回true（成功）或false（失败）
	 * </pre>
	 */
	List<Object> CRUD(String sign, String shql,Object...params);
	/**
	 * CRUD 增加（Create）、读取（Retrieve）（重新得到数据）、更新（Update）和删除（Delete）
	 * 通用的CRUD（增、查、改、删）方法 
	 * @param sign 
	 * <pre>
	 * 标识此次操作为C或R或U或D，只能有这四个选项
	 * </pre>
	 * @param hql  
	 * <pre>
	 * HQL语句，需要进行查询或删除时必须传入的参数，其它情况参数为null即可
	 * </pre>
	 * @param entity
	 * <pre>
	 * 查询或删除时：HQL语句需要参数既需条件的时候所传进来的实体类，HQL语句有哪些参数，实体类就必须赋哪些值；增加或修
	 * 改时：因无HQL语句，无须关联HQL语句参数，只需按数据库约束指定相关数据的实体类即可，若为？号赋参，则需用Object[]指定参数值
	 * </pre>
	 * @param p
	 * <pre>
	 * 用来实现分页的实体类，若不为null则进行分页计算，其中需指定初始值的属性为pageSize（页面数据行数）
	 * </pre>
	 * @return 
	 * <pre>
	 * 返回值为List&ltObject>的泛型集合，虽然是普通的数据类型，但返回后需处理的操作情况复杂而多。情况一：查询时，集合里数据以
	 * 实体对象来封装（实质都为Object类型） 情况二：增、删、改时：集合里数据只是普通的基本数据类型boolean，取值true或false
	 * </pre>
	 * <pre>
	 * 使用此方法例子：
	 * 查询所有信息: List&ltObject> list=cDao.CRUD("R","from UserInfo",null,null);
	 * 查询指定条件信息: List&ltObject> list=cDao.CRUD("R","from UserInfo where name=:name",null,null);
	 * 查询单个信息: UserInfo ui=cDao.CRUD("R","from UserInfo where id=:id",u,null);
	 *            u为实体类UserInfo实例化对象，u的id属性必须有指定的值，其他属性值可无
	 * 查询信息分页: List&ltObject> list=cDao.CRUD("R","from UserInfo",null,p);
	 *            p为实体类Paging实例化对象，需指定初始值的属性为pageSize（页面数据行数），默认当前页码pageIndex为第一页，
	 *            可自行更改
	 * 增加信息: boolean con=cDao.CRUD("C",null,u,null);
	 * 修改信息: boolean con=cDao.CRUD("U",null,u,null);
	 * 删除信息: boolean con=cDao.CRUD("D","from UserInfo where id=:id",u,null);
	 * </pre>
	 */
	List<Object> CRUD(String sign,String hql, Object entity,Paging p);
	/**
	 * 此方法重载于CRUD(String sign,String hql, Object entity,Paging p)，是这个方法的升级版本，使用该方法可同时做多个操作
	 * ，比如CRUD一起进行执行，如果执行都通过则提交事务，否则所有操作都回滚。也可做很复杂的操作，比如：RDURUDCRDU...操作。做RUD操作时
	 * 一次操作可重复执行N遍，N由传进来的实体类个数决定
	 * @param sign
	 *<pre>
	 * 为二维String型集合List<List<String>>，用来做操作标识。每个集合标识参数最多两个，第一个参数必须的，第二个参数
	 * 可选。第一个参数：标识此次操作为C或R或U或D，只能有这四个选项。第二个参数：标识R时使用的HQL语句，或标识D（删）操作时使用的HQL语句（使用原因
	 * 在ADU方法注释中已详细说明，此不再赘述）
	 *</pre>
	 * @param obj
	 *<pre>
	 * 为二维泛型集合List<List<Object>>，用来存储需要操作的对象。执行R操作时，每个集合标识参数最多两个，第一个参数为HQL语句参数所需要数据的对象
	 * （entity），第二个参数为数据需要分页的分页对象（Paging）；执行CUD操作时，每个集合里存储的都是需要操作的实体类对象，可N多个
	 *</pre>
	 * @return 
	 * 返回值为List<List<Object>>的二维泛型集合，用来存储执行后的结果，最多返回两个集合数据。第一个集合存储的数据为执行R操作后所得的结果集，第二个集
	 * 合存储的数据为CUD操作执行后所得的结果（取值true或false）
	 *<pre>
	 * 使用此方法例子：
	 * List&ltList&ltString>> sign=new  ArrayList&ltList&ltString>>();
	 * List&ltString> s1=new ArrayList&ltString>();
	 * s1.add(0,"C");
	 * s1.add(1,null);//可不写这个
	 * List&ltString> s2=new ArrayList&ltString>();
	 * s2.add(0,"U");
	 * s2.add(1,null);
	 * List&ltString> s3=new ArrayList&ltString>();
	 * s3.add(0,"R");
	 * s3.add(1,"from UserInfo where id=:id");
	 * List&ltString> s4=new ArrayList&ltString>();
	 * s4.add(0,"D");
	 * s4.add(1,"from UserInfo where id=:id");
	 * sign.add(s1);
	 * sign.add(s2);
	 * sign.add(s3);
	 * sign.add(s4);
	 * List&ltList&ltObject>> obj=new  ArrayList&ltList&ltObject>>();
	 * List&ltObject> o1=new ArrayList&ltObject>();
	 * o1.add(entity1);
	 * o1.add(entity2);
	 * o1.add(entity3);
	 * List&ltObject> o2=new ArrayList&ltObject>();
	 * o2.add(entity1);
	 * o2.add(entity2);
	 * List&ltObject> o3=new ArrayList&ltObject>();
	 * o3.add(userInfo);
	 * List&ltObject> o4=new ArrayList&ltObject>();
	 * o4.add(userInfo1);
	 * o4.add(userInfo2);
	 * o4.add(userInfo3);
	 * obj.add(o1);
	 * obj.add(o2);
	 * obj.add(o3);
	 * obj.add(o4);
	 * List&ltList&ltObject>> lists=cDao.CRUD(sign,obj);
	 * </pre>
	 */
	List<List<Object>> CRUD(List<List<String>> sign,List<List<Object>> obj);
}
