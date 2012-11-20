package org.isthere.connector.kipris;

import java.util.Map;

/**
 * Description.
 *
 * @author Edward KIM
 * @since 1.0
 */
public interface DetailPatentInfoGetter {

    Map<String, String> requestDetail(String masterKey);

}
