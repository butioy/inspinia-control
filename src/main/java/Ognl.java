import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by butioy on 2016/4/9.
 */
public class Ognl {

    public static boolean isNotEmpty( Object obj ) {
        boolean result = false;
        if( null == obj ) {
            result = false;
        } else if( obj instanceof String ) {
            String temp = (String) obj;
            if(StringUtils.isNotBlank(temp)) {
                result = true;
            }
        } else if( obj instanceof Map ) {
            Map temp = (Map) obj;
            if( !temp.isEmpty() && temp.size() > 0 ) {
                result = true;
            }
        } else if( obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof Double ) {
            result = true;
        }
        return result;
    }

}
