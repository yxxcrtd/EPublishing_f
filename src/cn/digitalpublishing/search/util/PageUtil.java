/**
 * 
 */
package cn.digitalpublishing.search.util;


/**
 * @author Paul Zhang
 *
 */
public class PageUtil {
	/**
	 * 获得查询起始位置
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static int getPageStart(int pageNumber, int pageSize) {
		return (pageNumber - 1) * pageSize;
	}
}
