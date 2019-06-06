package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.DateUtil;
import cn.digitalpublishing.ep.po.SSupplier;


public class SSupplierDao  extends CommonDao<SSupplier,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		
			Map<String,Object> table=new HashMap<String,Object>();
			String whereString="";
			List<Object> condition=new ArrayList<Object>();
			int flag=0;	
			if ((CollectionsUtil.exist(map, "issn")) && (!"".equals(map.get("issn"))) && (map.get("issn") != null) && (CollectionsUtil.exist(map, "eissn")) && (!"".equals(map.get("eissn"))) && (map.get("eissn") != null)) {
			      if (flag == 0) {
			        whereString = whereString + " where ( a.issn = ? or a.eissn = ? ) ";
			        flag = 1;
			      } else {
			        whereString = whereString + " and ( a.issn = ? or a.eissn = ? ) ";
			      }
			      condition.add(map.get("issn").toString());
			      condition.add(map.get("eissn").toString());
			    }
			/**
			 * 1.ID
			 */
			if(CollectionsUtil.exist(map, "supId")&&!"".equals(map.get("supId"))&&map.get("supId")!=null){
				if(flag==0){
					whereString+=" where a.id = ?";
					flag=1;
				}else{
					whereString+=" and a.id = ?";
				}
				condition.add(map.get("supId"));
			}
			/**
			 * 2.跨年查询
			 */
			if(CollectionsUtil.exist(map, "staryear")&&!"".equals(map.get("staryear"))&&map.get("staryear")!=null && CollectionsUtil.exist(map, "endyear")&&!"".equals(map.get("endyear"))&&map.get("endyear")!=null){
				
				if(flag==0){
					whereString+=" where a.sdate between ? and ? " ;
					flag=1;
				}else{
					whereString+=" and a.sdate between ? and ? " ;
				}
				condition.add(map.get("staryear"));
				condition.add(map.get("endyear"));
			}else if((CollectionsUtil.exist(map, "staryear")&&!"".equals(map.get("staryear"))&&map.get("staryear")!=null) ||( CollectionsUtil.exist(map, "endyear")&&!"".equals(map.get("endyear"))&&map.get("endyear")!=null)){
				if(flag==0){
					whereString+=" where a.sdate =  ? " ;
					flag=1;
				}else{
					whereString+=" and a.sdate =  ? " ;
				}
				if(!"".equals(map.get("staryear"))&&map.get("staryear")!=null){
					
					condition.add(map.get("staryear").toString());
				}else{
					condition.add(map.get("endyear").toString());
				}
			}
			/*if(CollectionsUtil.exist(map, "staryear")&&!"".equals(map.get("staryear"))&&map.get("staryear")!=null && CollectionsUtil.exist(map, "endyear")&&!"".equals(map.get("endyear"))&&map.get("endyear")!=null){
				
				if(flag==0){
					whereString+=" where a.sdate >=? and a.sdate <=? " ;
					flag=1;
				}else{
					whereString+=" and  a.sdate >=? and a.sdate <=? " ;
				}
				condition.add(map.get("staryear").toString());
				condition.add(map.get("endyear").toString());
			}else if((CollectionsUtil.exist(map, "staryear")&&!"".equals(map.get("staryear"))&&map.get("staryear")!=null) ||( CollectionsUtil.exist(map, "endyear")&&!"".equals(map.get("endyear"))&&map.get("endyear")!=null)){
				if(flag==0){
					whereString+=" where a.sdate =  ? " ;
					flag=1;
				}else{
					whereString+=" and a.sdate =  ? " ;
				}
				if(!"".equals(map.get("staryear"))&&map.get("staryear")!=null){
					
					condition.add(map.get("staryear").toString());
				}else{
					condition.add(map.get("endyear").toString());
				}
			}		*/
			/**
			 * 2.当月查询查询
			 */
			if(CollectionsUtil.exist(map, "datetime")&&map.get("datetime")!=null&&!"".equals(map.get("datetime"))){
				if(flag==0){
					whereString+=" where a.sdate =  ? " ;
					flag=1;
				}else{
					whereString+=" and a.sdate =  ? " ;
				}
				condition.add(map.get("datetime").toString());
			}
			
			
			
			
			/**
			 * 3.type
			 */
			if(CollectionsUtil.exist(map, "type")&&map.get("type")!=null&&(Integer)map.get("type")!=0){
				if(flag == 0){
					whereString += " where a.type = ?";
					flag = 1;
							
				}else{
					whereString +=" and a.type = ?";
				}
				condition.add(map.get("type"));
			}
			
			/**
			 * 4.sourceId
			 */
			if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))&&map.get("institutionId")!=null){
				if(flag==0){
					whereString+=" where a.institutionid = ?";
					flag=1;
				}else{
					whereString+=" and a.institutionid = ?";
				}
				condition.add(map.get("institutionId").toString().trim());
			}
			
			/**
			 * 5.contentId
			 */
			if(CollectionsUtil.exist(map, "pubId")&&!"".equals(map.get("pubId"))&&map.get("pubId")!=null){
				if(flag==0){
					whereString+=" where a.pubId = ?";
					flag=1;
				}else{
					whereString+=" and a.pubId = ?";
				}
				condition.add(map.get("pubId").toString().trim());
			}
			/***
			 * toc
			 */
			if(CollectionsUtil.exist(map, "tocON")&&!"".equals(map.get("tocON"))&&map.get("tocON")!=null){
				if(flag==0){
					whereString+=" where a.toc > ?";
					flag=1;
				}else{
					whereString+=" and a.toc > ?";
				}
				condition.add(map.get("tocON"));
			}
			/***
			 * searchON
			 */
			if(CollectionsUtil.exist(map, "searchON")&&!"".equals(map.get("searchON"))&&map.get("searchON")!=null){
				if(flag==0){
					whereString+=" where a.search > ?";
					flag=1;
				}else{
					whereString+=" and a.search > ?";
				}
				condition.add(map.get("searchON"));
			}
			/***
			 * accessON
			 */
			if(CollectionsUtil.exist(map, "accessON")&&!"".equals(map.get("accessON"))&&map.get("accessON")!=null){
				if(flag==0){
					whereString+=" where a.fullAccess > ?";
					flag=1;
				}else{
					whereString+=" and a.fullAccess > ?";
				}
				condition.add(map.get("accessON"));
			}
			/***
			 * refusedON
			 */
			if(CollectionsUtil.exist(map, "refusedON")&&!"".equals(map.get("refusedON"))&&map.get("refusedON")!=null){
				if(flag==0){
					whereString+=" where a.fullRefused > ?";
					flag=1;
				}else{
					whereString+=" and a.fullRefused > ?";
				}
				condition.add(map.get("refusedON"));
			}
			
			
			/***
			 * DownloadON
			 */
			if(CollectionsUtil.exist(map, "DownloadON")&&!"".equals(map.get("DownloadON"))&&map.get("DownloadON")!=null){
				if(flag==0){
					whereString+=" where a.download > ?";
					flag=1;
				}else{
					whereString+=" and a.download > ?";
				}
				condition.add(map.get("DownloadON"));
			}
			
			/**
			 * 书籍类型1-电子书  2-期刊 类型数组
			 */
			if(CollectionsUtil.exist(map, "pubtypes")&&map.get("pubtypes")!=null&&!"".equals(map.get("pubtypes").toString())){
				if(flag==0){
					whereString+=" where a.type in (";
					flag=1;
				}else{
					whereString+=" and a.type in (";
				}
				Integer[] pubtypes = (Integer[])map.get("pubtypes");
				for (int i =0; i<pubtypes.length; i++) {
					if(i<pubtypes.length-1){
						whereString+="?,";
					}else{
						whereString+="?";
					}
					condition.add(pubtypes[i]);
				}
				whereString+=")";
			}
			/**
			 * 书籍lang1-中文  2-外文
			 */
			if(CollectionsUtil.exist(map, "lang")&&map.get("lang")!=null){
				boolean isPrecisebook=CollectionsUtil.exist(map, "isPrecisebook")?Boolean.valueOf(map.get("isPrecisebook").toString()):false;
				String in="";
				if(isPrecisebook){
					if(flag==0){
						whereString+=" where a.lang in (";
						flag=1;
					}else{
						whereString+=" and a.lang in (";
					}
				}else{
					if(flag==0){
						whereString+=" where a.lang not in (";
						flag=1;
					}else{
						whereString+=" and a.lang not in (";
					}
				}
				
				
				String[] langs = (String[])map.get("lang");
				for(int i=0;i<langs.length;i++){
					if(i<langs.length-1){
						whereString+="?,";
					}else{
						whereString+="?";
					}
					condition.add(langs[i]);
				}
				whereString+=")";
			}
			
			table.put("where",whereString);
			table.put("condition", condition);
			return table;
		}
	
	/**
	 * 获取提供商统计表信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getList(Map<String,Object> condition,String sort)throws Exception{
		List<SSupplier> list=null;
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,institutionid,pubId,lang,title,type,author,isbn,issn,eissn,pubName,sdate,year,month,platform,fullAccess,fullRefused,refusedLicense,refusedConcurrent,search,searchStandard,searchFederal,toc,download ";
		String field=" a.id,a.institutionid,a.pubId,a.lang,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.sdate,a.year,a.month,a.platform,a.fullAccess,a.fullRefused,a.refusedLicense,a.refusedConcurrent,a.search,a.searchStandard,a.searchFederal,a.toc,a.download ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, SSupplier.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取提供商TOC统计信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getTocList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SSupplier> list=null;
		String minYear=condition.containsKey("staryear")&&condition.get("staryear")!=null&&!"".equals(condition.get("staryear").toString())?condition.get("staryear").toString():"";
		String maxYear=condition.containsKey("endyear")&&condition.get("endyear")!=null&&!"".equals(condition.get("endyear").toString())?condition.get("endyear").toString():"";
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" institutionid,pubId,title,type,author,isbn,issn,eissn,pubName,platform,count ";
		String field=" a.institutionid,a.pubId,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform";
		
		List<Object> con = new ArrayList<Object>();
		Map<String, Object> tt =this.getWhere(condition);
		for(Object o : (List<Object>)tt.get("condition")){
			con.add(o);
		}
		
		if(!"".equals(minYear)||!"".equals(maxYear)){
		
			field += ",(select cast(sum(s.toc) as string)  from SSupplier s "+ tt.get("where").toString().replace("a.", "s.") +" and  s.pubId=a.pubId  )";	
		}else{
			field+=",(select cast(sum(s.toc) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			
		}
		if(!"".equals(minYear)||!"".equals(maxYear)){
			for(Object o:(List<Object>)t.get("condition")){
				con.add(o);
			}
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, SSupplier.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取提供商全文统计信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getFullAccessList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SSupplier> list=null;
		String minYear=condition.containsKey("staryear")&&condition.get("staryear")!=null&&!"".equals(condition.get("staryear").toString())?condition.get("staryear").toString():"";
		String maxYear=condition.containsKey("endyear")&&condition.get("endyear")!=null&&!"".equals(condition.get("endyear").toString())?condition.get("endyear").toString():"";
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" institutionid,pubId,title,type,author,isbn,issn,eissn,pubName,platform,count ";
		String field=" a.institutionid,a.pubId,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform";
				
		List<Object> con = new ArrayList<Object>();
		Map<String, Object> tt =this.getWhere(condition);
		for(Object o : (List<Object>)tt.get("condition")){
			con.add(o);
		}
		
		if(!"".equals(minYear)||!"".equals(maxYear)){
			
			field += ",(select cast(sum(s.fullAccess) as string)  from SSupplier s "+ tt.get("where").toString().replace("a.", "s.") +" and  s.pubId=a.pubId  )";	
		}else{
			field+=",(select cast(sum(s.fullAccess) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			
		}
		if(!"".equals(minYear)||!"".equals(maxYear)){
			for(Object o:(List<Object>)t.get("condition")){
				con.add(o);
			}
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, SSupplier.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取提供商拒访统计信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getFullRefusedList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SSupplier> list=null;
		String minYear=condition.containsKey("staryear")&&condition.get("staryear")!=null&&!"".equals(condition.get("staryear").toString())?condition.get("staryear").toString():"";
		String maxYear=condition.containsKey("endyear")&&condition.get("endyear")!=null&&!"".equals(condition.get("endyear").toString())?condition.get("endyear").toString():"";
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" institutionid,pubId,title,type,author,isbn,issn,eissn,pubName,platform,count,counta,countb ";
		String field=" a.institutionid,a.pubId,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform";
		List<Object> con = new ArrayList<Object>();
		Map<String, Object> tt =this.getWhere(condition);
		if(!"".equals(minYear)||!"".equals(maxYear)){
		for(int i=0;i<3;i++){
			for(Object o : (List<Object>)tt.get("condition")){
				con.add(o);
			}	
			}
		}
		
		if(!"".equals(minYear)||!"".equals(maxYear)){
			field+= ", (select cast(sum(s.fullRefused) as string) from SSupplier s"+ tt.get("where").toString().replace("a.", "s.") +" and s.pubId=a.pubId )";
			field+=", (select cast(sum(s.refusedLicense) as string) from SSupplier s" + tt.get("where").toString().replace("a.", "s.") + " and s.pubId=a.pubId )";
			field+= ", (select cast(sum(s.refusedConcurrent) as string) from SSupplier s" + tt.get("where").toString().replace("a.", "s.") +" and s.pubId=a.pubId )";
			
		}else{
			field+=",(select cast(sum(s.fullRefused) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			field+=",(select cast(sum(s.refusedLicense) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			field+=",(select cast(sum(s.refusedConcurrent) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			
		}
		
			for(Object o:(List<Object>)t.get("condition")){
				con.add(o);
			}
		
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, SSupplier.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取提供商下载统计信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getDownloadList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SSupplier> list=null;
		String minYear=condition.containsKey("staryear")&&condition.get("staryear")!=null&&!"".equals(condition.get("staryear").toString())?condition.get("staryear").toString():"";
		String maxYear=condition.containsKey("endyear")&&condition.get("endyear")!=null&&!"".equals(condition.get("endyear").toString())?condition.get("endyear").toString():"";
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" institutionid,pubId,title,type,author,isbn,issn,eissn,pubName,platform,count ";
		String field=" a.institutionid,a.pubId,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform";
		
		List<Object> con = new ArrayList<Object>();
		Map<String, Object> tt =this.getWhere(condition);
		for(Object o : (List<Object>)tt.get("condition")){
			con.add(o);
		}
		
		if(!"".equals(minYear)||!"".equals(maxYear)){
			
			field += ",(select cast(sum(s.download) as string)  from SSupplier s "+ tt.get("where").toString().replace("a.", "s.") +" and  s.pubId=a.pubId  )";	
		}else{
			field+=",(select cast(sum(s.download) as string)  from SSupplier s where s.pubId=a.pubId ) ";
			
		}
		if(!"".equals(minYear)||!"".equals(maxYear)){
			for(Object o:(List<Object>)t.get("condition")){
				con.add(o);
			}
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, SSupplier.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取提供商搜索统计信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SSupplier> getSearchList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SSupplier> list=null;
		String minYear=condition.containsKey("staryear")&&condition.get("staryear")!=null&&!"".equals(condition.get("staryear").toString())?condition.get("staryear").toString():"";
		String maxYear=condition.containsKey("endyear")&&condition.get("endyear")!=null&&!"".equals(condition.get("endyear").toString())?condition.get("endyear").toString():"";
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" institutionid,pubId,title,type,author,isbn,issn,eissn,pubName,platform,count,counta,countb ";
		String field=" a.institutionid,a.pubId,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ";
		List<Object> con = new ArrayList<Object>();
		Map<String, Object> tt =this.getWhere(condition);
		if(!"".equals(minYear)||!"".equals(maxYear)){
			for(int i=0;i<3;i++){
				for(Object o : (List<Object>)tt.get("condition")){
					con.add(o);
				}	
			}
		}
		
		
		 if(!"".equals(minYear)||!"".equals(maxYear)){
			field+= ", (select cast(sum(s.search) as string) from SSupplier s"+ tt.get("where").toString().replace("a.", "s.") +" and s.pubId=a.pubId )";
			field+=", (select cast(sum(s.searchStandard) as string) from SSupplier s" + tt.get("where").toString().replace("a.", "s.") + " and s.pubId=a.pubId )";
			field+= ", (select cast(sum(s.searchFederal) as string) from SSupplier s" + tt.get("where").toString().replace("a.", "s.") +" and s.pubId=a.pubId )";
			
		}else{
			field+=",(select cast(sum(s.search) as string )  from SSupplier s where s.pubId=a.pubId ) ";
			field+=",(select cast(sum(s.searchStandard) as string )  from SSupplier s where s.pubId=a.pubId ) ";
			field+=",(select cast(sum(s.searchFederal) as string )  from SSupplier s where s.pubId=a.pubId ) ";
			
		}
	
			for(Object o:(List<Object>)t.get("condition")){
				con.add(o);
			}
		
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, SSupplier.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
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
		List<SSupplier> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from SSupplier a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", SSupplier.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	/**
	 * 获取统计总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getGroupbyCount(Map<String,Object> condition,String group)throws Exception{
		int result=0;
		String hql=" from SSupplier a ";
		Map<String,Object> t=this.getWhere(condition);		
		String property=" id ";
		String field = " cast(count(*) as string) ";
		try{
			List<SSupplier> list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),group, SSupplier.class.getName());
			if(list!=null){
				result=list.size();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
}
