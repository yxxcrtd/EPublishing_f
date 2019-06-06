package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PRelatedPublisher;

public class PRelatedPublisherDao extends CommonDao<PRelatedPublisher,String>{
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "position")&&!"".equals(map.get("position"))){
			if(flag==0){
				whereString+=" where a.position = ?";
				flag=1;
			}else{
				whereString+=" and a.position = ?";
			}
			condition.add(map.get("position"));
		}
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取配置列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PRelatedPublisher> getList(Map<String,Object> condition,String sort)throws Exception{
		List<PRelatedPublisher> list=null;
		String hql=" from PRelatedPublisher a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,title,picPath,url,position,orderMark";
		String field="a.id,a.title,a.picPath,a.url,a.position,a.orderMark";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PRelatedPublisher.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取分页信息
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PRelatedPublisher> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PRelatedPublisher> list=null;
		String hql=" from PRelatedPublisher a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,title,picPath,url,position,orderMark";
		String field="a.id,a.title,a.picPath,a.url,a.position,a.orderMark";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PRelatedPublisher.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<PRelatedPublisher> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PRelatedPublisher a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PRelatedPublisher.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	/**
	 * 同步数据到CNPE
	 * @param id
	 * @throws Exception
	 */
	public void SYNCTOCNPE(String id)throws Exception {
		try{
			this.hibernateDao.executeSql("{call SYNC_REPUB_TO_CNPE(?)}",new Object[]{id});
			/**
			 * CREATE OR REPLACE 
PROCEDURE SYNC_REPUB_TO_CNPE
(DCC_REPUBID_ID  VARCHAR2)
AS
BEGIN
	DELETE FROM EPUB_P_RELATEDPUBLISHER@CNPE_LINK WHERE RELATEDPUBLISHER_ID=DCC_REPUBID_ID;
	INSERT INTO EPUB_P_RELATEDPUBLISHER@CNPE_LINK (
  RELATEDPUBLISHER_ID,
  RELATEDPUBLISHER_TITLE,
  RELATEDPUBLISHER_PICPATH,
  RELATEDPUBLISHER_URL,
  RELATEDPUBLISHER_POSITION,
  RELATEDPUBLISHER_ORDERMARK
  )SELECT 
  RELATEDPUBLISHER_ID,
  RELATEDPUBLISHER_TITLE,
  RELATEDPUBLISHER_PICPATH,
  RELATEDPUBLISHER_URL,
  RELATEDPUBLISHER_POSITION,
  RELATEDPUBLISHER_ORDERMARK 
  FROM DCC_P_RELATEDPUBLISHER WHERE RELATEDPUBLISHER_ID=DCC_REPUBID_ID;
  COMMIT;
END SYNC_REPUB_TO_CNPE;
			 */
			
		}catch (Exception e) {
			throw e;
		}
	}

}
