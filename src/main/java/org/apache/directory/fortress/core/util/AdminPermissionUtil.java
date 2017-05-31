package org.apache.directory.fortress.core.util;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.directory.fortress.annotation.AdminPermissionOperation;
import org.apache.directory.fortress.core.impl.AccessMgrImpl;
import org.apache.directory.fortress.core.impl.AdminMgrImpl;
import org.apache.directory.fortress.core.impl.AuditMgrImpl;
import org.apache.directory.fortress.core.impl.DelAccessMgrImpl;
import org.apache.directory.fortress.core.impl.DelAdminMgrImpl;
import org.apache.directory.fortress.core.impl.DelReviewMgrImpl;
import org.apache.directory.fortress.core.impl.GroupMgrImpl;
import org.apache.directory.fortress.core.impl.PwPolicyMgrImpl;
import org.apache.directory.fortress.core.impl.ReviewMgrImpl;


public class AdminPermissionUtil
{

    public static Map<String, List<String>> getPossibleAdminOperations()
    {
        Map<String, List<String>> adminOperations = new HashMap<String, List<String>>();
        
        adminOperations.put( AccessMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( AccessMgrImpl.class ) );
        adminOperations.put( AdminMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( AdminMgrImpl.class ) );
        adminOperations.put( AuditMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( AuditMgrImpl.class ) );
        adminOperations.put( DelAdminMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( DelAdminMgrImpl.class ) );
        adminOperations.put( DelAccessMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( DelAccessMgrImpl.class ) );
        adminOperations.put( DelReviewMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( DelReviewMgrImpl.class ) );
        adminOperations.put( GroupMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( GroupMgrImpl.class ) );
        adminOperations.put( PwPolicyMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( PwPolicyMgrImpl.class ) );
        adminOperations.put( ReviewMgrImpl.class.getCanonicalName(), AdminPermissionUtil.getOperations( ReviewMgrImpl.class ) );
        
        return adminOperations;
    }


    private static List<String> getOperations( Class clazz )
    {
        List<String> operations = new ArrayList<String>();
        
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        for ( final Method method : declaredMethods )
        {
            if ( method.isAnnotationPresent( AdminPermissionOperation.class ) )
            {
                AdminPermissionOperation annotation = method.getAnnotation( AdminPermissionOperation.class );
                if(annotation.operationName() != null && !annotation.operationName().isEmpty()){
                    operations.add( annotation.operationName() );
                }
                else{
                    operations.add( method.getName() );
                }
            }
        }
        
        return operations;
    }

}
