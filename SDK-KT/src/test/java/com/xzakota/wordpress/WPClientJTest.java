package com.xzakota.wordpress;

import com.xzakota.collect.KStrVObj;
import com.xzakota.model.Authentication;
import com.xzakota.net.ResponseTarget;
import com.xzakota.wordpress.model.*;
import com.xzakota.wordpress.model.status.ItemStatus;
import com.xzakota.wordpress.model.status.Status;
import com.xzakota.wordpress.request.Media;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WPClientJTest {
    public static WPClient client;

    @BeforeAll
    public static void setup() throws Exception {
        Dotenv env = Dotenv.load();
        String server = env.get("SERVER_HTTPS_URL");
        String username = env.get("AUTH_USERNAME");
        String password = env.get("AUTH_APPLICATION_PASSWORD");

        if (server == null || username == null || password == null) {
            throw new RuntimeException("Please configure .env file!");
        }

        client = new WPClient(server, new Authentication(username, password));
        client.setUserAgent("Test " + client.getUserAgent());
        client.setDebug(true);
        client.connectTest();
        divide();
    }

    @AfterAll
    public static void release() {
        client.disconnect();
    }

    public static void divide() {
        println("-".repeat(80));
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    @Test
    public void userTest() {
        User user = client.request().users().retrieveById(1L);
        if (user != null) {
            println("The first user of the website: ");
            println(user.asMap());
        }
        divide();
    }

    @Test
    public void mediaTest() {
        Media mediaRouter = client.request().media();
        List<Medium> media = mediaRouter.list();
        println("List of Medium: ");
        println(media);

        File file = new File("/path/test.png");
        if (file.exists() && file.isFile()) {
            Medium newMedium = new Medium();
            newMedium.setResource(file);
            newMedium.setTitle(new RenderedField("test"));
            Medium uploadedMedium = mediaRouter.create(newMedium);
            println("Uploaded new file: " + uploadedMedium);
        }

        divide();
    }

    @Test
    public void pageTest() {
        Status[] status = new Status[] {ItemStatus.PRIVATE, ItemStatus.PUBLISH};
        List<Page> pages = client.request().pages().listByStatus(status);
        println("List of Page" + Arrays.toString(status) + ": ");
        println(pages);
        divide();
    }

    @Test
    public void postTest() {
        Post post = client.request().posts().retrieveById(1L);
        if (post != null) {
            println("Post with ID 1: " + post);
        } else {
            println("No post with ID 1.");
        }
        divide();
    }

    @Test
    public void themeTest() {
        client.request(request -> request.router("/themes", ResponseTarget.class, router -> {
            List<ResponseTarget> themes = router.list(KStrVObj.of());
            List<Object> themeStylesheet = themes.stream().map(obj -> {
                KStrVObj allFields = obj.getAllFields();
                return allFields != null ? allFields.get("stylesheet") : null;
            }).toList();
            println(themeStylesheet);
        }));
        divide();
    }
}
