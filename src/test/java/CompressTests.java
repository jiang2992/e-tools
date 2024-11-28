import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import lombok.SneakyThrows;
import org.jiang.tools.compress.GzipUtils;
import org.jiang.tools.data.EasyData;
import org.junit.Test;

/**
 * 压缩测试类
 *
 * @author Bin
 * @since 1.1.3
 */
public class CompressTests {

    /**
     * gzip压缩
     */
    @Test
    @SneakyThrows
    public void gzipTest() {
        EasyData sourceData = EasyData.of("午后的阳光懒洋洋地洒在小镇的街道上，空气中弥漫着淡淡的花香和烤面包的香气。街角的咖啡馆里，老约翰正一边修理着他的老式自行车，一边哼着不知名的曲调。他那双浑浊的双眼，却仿佛洞悉着世间的一切。\n" +
                "隔壁桌的年轻女孩，正沉浸在书本的世界里，她的脸上写满了专注和沉思。偶尔，她会抬起头，望向窗外，嘴角泛起一抹微笑，似乎在回忆着什么美好的过往。\n" +
                "咖啡馆的老板娘，一个身材丰满，性格爽朗的女人，正忙着招呼着客人，她的脸上始终洋溢着热情和真诚。她会记得每一个客人的喜好，也会用她独特的幽默感，将客人逗得哈哈大笑。\n" +
                "店里其他的客人，各怀心事，却又在彼此的交谈中，找到了共鸣和慰藉。年迈的老人，回忆着往昔的辉煌，年轻的情侣，憧憬着未来的美好，孩子们，嬉戏打闹，充满了活力和希望。\n" +
                "时间在咖啡馆里静静流淌，仿佛一切都被这温暖的光线和香气所包围。每个人都沉浸在自己的世界里，却也与周围的人，有着微妙的联系。这平凡的小镇，这温馨的咖啡馆，见证着人生百态，也承载着人们的喜怒哀乐。\n" +
                "夕阳西下，咖啡馆的灯光亮起，为这个宁静的小镇，增添了一丝暖意。老约翰骑着他的老式自行车，缓缓地离开了咖啡馆，他的背影，仿佛融入了这美丽的夕阳之中。一天就这样过去了，但故事仍在继续，在小镇的每个角落，每个人的心中，都在上演着不同的精彩。" +
                "夜幕降临，小镇的街道上亮起了路灯，将原本昏暗的街道照亮得如同白昼。路灯下，一排排高大的梧桐树，仿佛士兵般守卫着这座宁静的小镇。\n" +
                "街角的便利店里，店员小丽正在整理货架。她今年才20岁，却已经在这家便利店工作了三年。她沉默寡言，性格内向，但工作认真负责，深得老板的信任。\n" +
                "店外，一辆红色的跑车缓缓地驶来，停在了便利店门口。车门打开，走下一个年轻男子，他穿着黑色的皮衣，戴着墨镜，看起来十分酷炫。他走进店里，买了一盒香烟和一瓶饮料，然后便匆匆离开了。\n" +
                "小丽看着男子离去的背影，心中泛起一丝好奇。她不知道这个男人是谁，也不知道他要去哪里。但他的出现，却让这个平淡的夜晚，增添了一丝神秘的色彩。\n" +
                "时间一分一秒地过去，便利店里的顾客渐渐减少。小丽收拾着店里的东西，准备下班。这时，店门突然被人推开，一个穿着灰色风衣，带着鸭舌帽的男人走了进来。\n" +
                "男人走到柜台前，低声对小丽说道：“我要找一个人，一个叫王浩的人。”\n" +
                "小丽愣了一下，她不知道这个人是谁，更不知道王浩是谁。她摇摇头，说道：“对不起，我不知道你在说什么。”\n" +
                "男人没有说话，只是盯着小丽的眼睛，眼神中透着一丝凌厉。小丽被他盯得有些发毛，忍不住往后退了一步。\n" +
                "“你最好告诉我，你见过他吗？”男人声音低沉，带着一丝威胁。\n" +
                "小丽心中害怕，但还是强忍着恐惧，说道：“我真的没有见过他。”\n" +
                "男人沉默了片刻，突然转身离开了便利店。小丽看着他的背影，心中忐忑不安。她不知道这个男人到底是谁，也不知道他为什么要找王浩。但直觉告诉她，这个男人绝非善类。\n" +
                "小丽深吸一口气，决定报警。她拿起电话，拨打了报警电话，将事情一五一十地告诉了警察。\n" +
                "夜色越来越浓，小丽站在便利店门口，目送着警车远去。她不知道接下来会发生什么，但她相信，警察会将一切真相调查清楚。");
        System.out.println("source data size:" + sourceData.value().length);

        EasyData easyData = GzipUtils.compress(sourceData);
        System.out.println("compressed data size:" + easyData.value().length);

        sourceData = GzipUtils.decompress(easyData);
        System.out.println("decompressed data size:" + sourceData.value().length);

        System.out.println(sourceData.stringValue());
    }

    /**
     * 大文件gzip压缩
     */
    @Test
    @SneakyThrows
    public void bigFileGzipTest() {
        File sourceFile = new File("/Users/bin/WorkSpace/test.data");
        File targetFile = new File("/Users/bin/WorkSpace/test.data.gzip");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        targetFile.createNewFile();
        System.out.println("source file size: " + sourceFile.length() / (1024 * 1024) + "mb");
        long start = System.currentTimeMillis();
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            GzipUtils.compress(inputStream, outputStream);
            outputStream.flush();
        }
        long end = System.currentTimeMillis();
        System.out.println("compress time: " + (end - start) + "ms");
        System.out.println("target file size: " + targetFile.length() / (1024 * 1024) + "mb");
    }

}
