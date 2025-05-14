package com.ariari.ariari.commons.server;

import java.util.List;

public class SuspiciousUriDetector {

    private static final List<String> suspiciousUriList = List.of(
            // CMS 관련
            "xe/index.php", "wp-login.php", "wp-admin", "wordpress/",
            "joomla/", "drupal/", "typo3/", "cms/", "sitecore/",
            "admin/", "admin.php", "admin/login", "administrator/",
            "user/login", "login.php", "index.php", "panel/login",

            // 백도어 / 악성 파일
            "shell.php", "cmd.php", "eval.php", "upload.php",
            "backdoor.php", "hack.php", "malicious.php",

            // 환경 설정 / 민감 정보
            ".env", "phpinfo.php", "info.php", "config.php",
            "config.json", "web.config", "settings.py", ".git/config",

            // DB 백업 및 관리도구
            "backup.sql", "db.sql", "dump.sql", "database.sql",
            "adminer.php", "phpmyadmin/", "mysql/", "sql/",

            // 디렉터리/파일 노출
            ".git/", ".svn/", ".hg/", ".DS_Store", "CVS/",
            "storage/", "logs/", "files/", "uploads/", "tmp/",

            // AWS 관련
            ".aws/credentials", "aws-config", "aws-secret", ".aws/config",

            // 스프링 부트 actuator
            "actuator", "actuator/health", "actuator/env", "actuator/metrics",

            // 로컬 테스트 파일
            "test.php", "dev.php", "debug.php", "test/", "dev/", "debug/",

            // 웹서버 정보
            "server-status", "server-info", "status", "healthz", "readyz",

            // 크롤링용 탐색
            "robots.txt", "sitemap.xml", "security.txt",

            // 기타
            "cgi-bin/", "cgi/", "wp-content/", "wp-includes/",
            "license.txt", "readme.html", "README.md", "install.php",
            "composer.json", "package.json", "node_modules/"
    );

    public static boolean isSuspiciousUri(String requestUri) {
        if (requestUri == null) return false;
        String lowerUri = requestUri.toLowerCase();
        return suspiciousUriList.stream()
                .anyMatch(lowerUri::contains);
    }
}
