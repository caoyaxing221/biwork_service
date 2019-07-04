package com.biwork.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

	 /**
     * APP登录Token的生成和解析
     * 
     */

    /** token秘钥，请勿泄露，请勿随便修改  */
    public static final String SECRET = PropertiesUtil.getProperty("jwtkey");
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     * 
     * JWT构成: header, payload, signature
     * 
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id,Long role_id) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
                .withClaim("role_id", null == role_id ? null : role_id.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
             //e.printStackTrace();
        	return null;
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     * 
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }
    /**
     * 根据Token获取user_id
     * 
     * @param token
     * @return user_id
     */
    public static Long getRoleID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim role_id_claim = claims.get("role_id");
        if (null == role_id_claim || StringUtils.isEmpty(role_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(role_id_claim.asString());
    }
  public static void main(String[] args) {
//	try {
//		System.out.print(createToken(Long.parseLong("123")));
//	} catch (NumberFormatException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	  //getAppUID("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTIzIiwiaXNzIjoiU2VydmljZSIsImV4cCI6MTUzNTM0MzI2MSwiaWF0IjoxNTM0NDc5MjYxfQ.KFx9gW7qkKof7TO_LYZX3YIDMkmdLvFSDByVgP-0Oz0")
	  System.out.print(verifyToken("eaJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTIzIiwiaXNzIjoiU2VydmljZSIsImV4cCI6MTUzNTM0MzI2MSwiaWF0IjoxNTM0NDc5MjYxfQ.KFx9gW7qkKof7TO_LYZX3YIDMkmdLvFSDByVgP-0Oz0"));
  }
}
