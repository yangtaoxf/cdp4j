/**
 * cdp4j Commercial License
 *
 * Copyright 2018 WebFolder OÜ
 *
 * Permission is hereby granted, to "____"
 * obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute and sublicense of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.sample;

import static io.webfolder.cdp.type.constant.DownloadBehavior.Allow;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Page;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class DownloadFile {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html");
            session.waitDocumentReady();
            session.getCommand().getNetwork().enable();
            Page page = session.getCommand().getPage();
            Path downloadPath = Paths.get(".").toAbsolutePath();
            page.setDownloadBehavior(Allow, downloadPath.toString());
            // link must be visible before downloading the file
            session.evaluate("document.querySelector(\"code\").scrollIntoView()");
            // click the download link
            session.click("code");
            session.wait(2000 * 200);
        }
    }
}
