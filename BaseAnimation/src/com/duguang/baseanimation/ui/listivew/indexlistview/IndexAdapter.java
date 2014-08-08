package com.duguang.baseanimation.ui.listivew.indexlistview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AlphabetIndexer;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.SimpleAdapter;

/**
 * AlphabetIndexer,实现了SectionIndexer接口,是adapter的一个辅助类,辅助实现在快滑时，显示索引字母。
 * 使用字母索引的话，必须保证数据列表是按字母顺序排序，以便AlphabetIndexer采用二分查找法快速定位。
 * @author Administrator
 */
class IndexAdapter extends SimpleAdapter implements SectionIndexer{
    
    private AlphabetIndexer alphabetIndexer;
    /**
     * @param context 	  上下文
     * @param data 	 	 ListView中的数据
     * @param resource   ListView中条目的资源id
     * @param from		 Map集合中的键
     * @param to		  条目中子控件的id组成的集合
     */
    public IndexAdapter(Context context,List<? extends Map<String, ?>> data, int resource,String[] from, int[] to) {
        super(context, data, resource, from, to);
        //设置数据游标
        //设置索引字母列表
        /**
         * Cursor表示数据游标
         * sortedColumnIndex按字母索引的游标中的列号
         * alphabet字母列表，用的最多的是"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
         */
        alphabetIndexer = new AlphabetIndexer(new IndexCursor(this), 0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
    
    /**
     * 返回构造函数中指定的由字母构成的区段数组。
     */
    @Override
    public Object[] getSections() {
        return alphabetIndexer.getSections();
    }
    
    /**
     * 执行二进制检索或查找索引来找出匹配给定节首字母的第一行数据
     * @param sectionIndex	要检索的节索引
     */
    @Override
    public int getPositionForSection(int section) {
        return alphabetIndexer.getPositionForSection(section);
    }
    /**
     * 节索引.如果 position 越界，返回的节索引必须在节数组大小范围内
     * position	要查找的列表位置
     */
    @Override
    public int getSectionForPosition(int position) {
        return alphabetIndexer.getSectionForPosition(position);
    }
    
    /**
     * 伪装一个Cursor供AlphabetIndexer作数据索引源
     */
    private class IndexCursor implements Cursor{
        
        private ListAdapter adapter;
        private int position;
        private Map<String, String> map;
        
        public IndexCursor(ListAdapter adapter){
            this.adapter = adapter;
        }

        @Override
        public int getCount() {return this.adapter.getCount();}
        
        /**
         * 取得索引字母，这个方法非常重要，根据实际情况具体处理
         */
        @SuppressWarnings("unchecked")
        @Override
        public String getString(int columnIndex) {
            map = (HashMap<String, String>)adapter.getItem(position);
            return map.get("itemText").substring(0,1);
        }
        
        @Override
        public boolean moveToPosition(int position) {
            if(position<-1||position>getCount()){
                return false;
            }
            
            this.position = position;
            //如果不满意位置有点向上偏的话，下面这几行代码是修复定位索引值为顶部项值的问题
            //if(position+2>getCount()){                
            //    this.position = position;
            //}else{
            //   this.position = position + 2;
            //}
            return true;
        }

		@Override
		public int getPosition() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean move(int offset) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean moveToFirst() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean moveToLast() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean moveToNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean moveToPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isFirst() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLast() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isBeforeFirst() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAfterLast() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getColumnIndex(String columnName) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnIndexOrThrow(String columnName)
				throws IllegalArgumentException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getColumnName(int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getColumnNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public byte[] getBlob(int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public short getShort(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getInt(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getLong(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getFloat(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getDouble(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getType(int columnIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isNull(int columnIndex) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void deactivate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		@Deprecated
		public boolean requery() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isClosed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void registerContentObserver(ContentObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregisterContentObserver(ContentObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setNotificationUri(ContentResolver cr, Uri uri) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean getWantsAllOnMoveCalls() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Bundle getExtras() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Bundle respond(Bundle extras) {
			// TODO Auto-generated method stub
			return null;
		}
        
    }
}
