package com.xiesx.springboot.support.token;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Maps;
import com.xiesx.springboot.utils.DateUtils;

import io.jsonwebtoken.*;

/**
 * @title TokenStorageHelper
 * @description https://jwt.io/
 * @author XIE
 * @date 2020年5月7日上午10:42:17
 */
public class JwtHelper {

    /**
     * jwt密钥
     */
    public static final String JWT_SECRET = "fast-jwt";

    /**
     * jwt有效时间
     */
    public static final long JWT_EXPIRE_M_1 = 1 * 60 * 1000; // 1分钟

    public static final long JWT_EXPIRE_M_5 = 5 * JWT_EXPIRE_M_1; // 5分钟

    public static final long JWT_EXPIRE_H_1 = 1 * 60 * 60 * 1000; // 1小时

    public static final long JWT_EXPIRE_H_12 = 12 * JWT_EXPIRE_H_1; // 12小时

    public static final long JWT_EXPIRE_D_1 = 24 * 60 * 60 * 1000; // 1天

    public static final long JWT_EXPIRE_D_7 = 7 * JWT_EXPIRE_D_1; // 7天

    public static final long JWT_EXPIRE_D_30 = 30 * JWT_EXPIRE_D_1; // 一个星期

    /**
     * jwt生成方
     */
    private final static String JWT_ISSUER = "xiesx";

    /**
     * 生成jwt
     *
     * @return
     */
    public static String create(String audience, String subject) {
        return create(JWT_ISSUER, audience, subject, null, null, JWT_EXPIRE_D_1);
    }

    public static String create(String audience, String subject, long timeout) {
        return create(JWT_ISSUER, audience, subject, null, null, timeout);
    }

    public static String create(String audience, String subject, Map<String, Object> claims, long timeout) {
        return create(JWT_ISSUER, audience, subject, null, claims, timeout);
    }

    public static String create(String audience, String subject, Map<String, Object> header, Map<String, Object> claims,
            long timeout) {
        return create(JWT_ISSUER, audience, subject, header, claims, timeout);
    }

    public static String create(String issuer, String audience, String subject, Map<String, Object> header,
            Map<String, Object> claims, long timeout) {
        //
        String jwtid = UUID.randomUUID().toString();
        //
        Long timemillis = System.currentTimeMillis();
        Date staDate = new Date(timemillis);
        Date endDate = new Date(System.currentTimeMillis() + timeout);
        //
        JwtBuilder builder = Jwts.builder()
                .setId(jwtid) // 唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setSubject(subject) // 主题
                .setIssuer(issuer) // 签发者
                .setAudience(audience) // 接收者
                .setIssuedAt(staDate) // 签发时间
                .setExpiration(endDate) // 过期时间
                .addClaims(claims)// 私有属性
                .signWith(SignatureAlgorithm.HS256, key());// 签名算法以及密匙

        // 设置头部信息
        if (ObjectUtils.isNotEmpty(header)) {
            builder.setHeader(header);
        }
        return builder.compact();
    }

    /**
     * 解析
     *
     * @param token
     * @return
     */
    public static Claims parser(String token) throws JwtException {
        Claims claimsJws = Jwts.parser().setSigningKey(key()).parseClaimsJws(token).getBody();
        return claimsJws;
    }

    /**
     * 过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * 生成加密key
     *
     * @return
     */
    public static SecretKey key() {
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static void main(String[] args) {
        //
        Map<String, Object> header = Maps.newConcurrentMap();
        header.put("jwt", "xxx");
        //
        Map<String, Object> claims = Maps.newConcurrentMap();
        claims.put("userid", "1");
        claims.put("username", "136305973");
        claims.put("nickname", "管理员");
        //
        String token = create("zedu", "api");
        token = create("zedu", "api", 60 * 1000);
        token = create("zedu", "api", claims, 60 * 1000);
        token = create("zedu", "api", claims, 60 * 1000);
        token = create("zedu", "api", header, claims, 60 * 1000);
        System.out.println(token);

        Claims c = parser(
                "eyJqd3QiOiJ4eHgiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4YzBjYTc4Yy02YTdmLTQ1MzMtYWY3YS02MTkwMmMxYWQ5YzIiLCJzdWIiOiJhcGkiLCJpc3MiOiJ4aWVzeCIsImF1ZCI6InplZHUiLCJpYXQiOjE1ODg4MjExOTYsImV4cCI6MTU4ODgyMTI1Niwibmlja25hbWUiOiLnrqHnkIblkZgiLCJ1c2VyaWQiOiIxIiwidXNlcm5hbWUiOiIxMzYzMDU5NzMifQ.hLnP_1NEbC_mEM28H3uqvP67C9dDyM2z-yspvj_ycsI");
        System.out.println("jti用户id：" + c.getId());
        System.out.println("sub主题：" + c.getSubject());
        System.out.println("iss签发者：" + c.getIssuer());
        System.out.println("aud接收者：" + c.getAudience());

        System.out.println("iat登录时间：" + DateUtils.format(c.getIssuedAt()));
        System.out.println("exp过期时间：" + DateUtils.format(c.getExpiration()));
        //
        System.out.println("exp是否过期时间：" + isExpired(c.getExpiration()));
        //
        System.out.println("用户Id：" + c.get("user_id"));
        System.out.println("用户名：" + c.get("user_name"));
        System.out.println("用户名：" + c.get("nick_name", String.class));
    }
}
