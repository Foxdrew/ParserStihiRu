package org.foxdrew;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.foxdrew.page.PoemPage;
import org.foxdrew.utils.StringWorker;
import org.foxdrew.utils.Tuple2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Poem implements IWriter {
    private final String title;
    private final String author;
    private final String registrationId;
    private final String text;
    private final Image image;

    public String getTitle() {
        return title;
    }

    public Poem(String title, String author, String registrationId, String text, Image image) {
        this.title = title;
        this.author = author;
        this.registrationId = registrationId;
        this.text = text;
        this.image = image;
    }

    public static Poem parse(PoemPage page) throws IOException {
        String title = page.getTitle();
        String author = page.getAuthorName();
        String registrationId = page.getRegistrationId();

        String text = page.getPoemText();
        text = StringWorker.clearHtmlTags(text);

        String imgHref = page.getImageHref();

        Image image = null;
        if (imgHref != null) {
            image = Image.download(imgHref);
        }

        Poem result = null;

        if (title != null && author != null && registrationId != null && text != null) {
            result = new Poem(title, author, registrationId, text, image);
        }

        return result;
    }

    @Override
    public void write(FileOutputStream out) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph;
        XWPFRun run;

        if(image != null) {
            paragraph = document.createParagraph();
            run = paragraph.createRun();

            Tuple2<Double, Double> size = image.getResizedSize(450, 200);

            try {
                InputStream is = image.getByteArrayInputStream();
                run.addPicture(is,
                        Workbook.PICTURE_TYPE_JPEG,
                        "Photo", Units.toEMU(size.getFirst()),
                        Units.toEMU(size.getSecond()));
            } catch (Exception e) {

            }
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();

        run.setBold(true);
        run.setFontSize(18);
        run.setText(title);

        paragraph = document.createParagraph();
        run = paragraph.createRun();

        run.setItalic(true);
        run.setFontSize(18);
        run.setText(author);

        paragraph = document.createParagraph();
        run = paragraph.createRun();

        run.setFontSize(16);
        String[] splitted = text.split("\\n");
        for (int i = 0; i < splitted.length; i++) {
            run.setText(splitted[i]);
            run.addCarriageReturn();
        }

        paragraph = document.createParagraph();
        run = paragraph.createRun();

        run.setBold(true);
        run.setFontSize(16);
        run.setText("Свидетельство о публикации " + registrationId);

        try {
            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}