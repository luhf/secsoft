package com.secsoft.cms.common.generator;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.secsoft.CmsConfig;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author luhf
 * @since 2019/08/03 0:26
 */
public class GeneratorTest {
    /**
     * model包名
     */
    private final static String MODEL_PACKAGE_NAME = "com.secsoft.cms.common.model";
    /**
     * 部分功能使用 Db + Record 模式实现，无需生成 model 的 table 在此配置
     */
    private final static String[] EXCLUDED_TABLE = {};

    /**
     * 重用 JFinalClubConfig 中的数据源配置，避免冗余配置
     */
    private static DataSource getDataSource() {
        HikariCpPlugin hikariCpPlugin = CmsConfig.getHikariCpPlugin();
        hikariCpPlugin.start();
        return hikariCpPlugin.getDataSource();
    }

    @Test
    public void testGen() {
        // base model 所使用的包名
        String baseModelPackageName = MODEL_PACKAGE_NAME.concat(".base");
        // base model 文件保存路径
        String baseModelOutputDir =
            PathKit.getWebRootPath().concat("/src/main/java/".concat(baseModelPackageName.replace(".", "/")));
        System.out.println("输出路径：" + baseModelOutputDir);
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir.concat("/..");
        // 数据源
        DataSource dataSource = getDataSource();

        // 创建生成器
        com.jfinal.plugin.activerecord.generator.Generator gen = new com.jfinal.plugin.activerecord.generator.Generator(
            dataSource, baseModelPackageName, baseModelOutputDir, MODEL_PACKAGE_NAME, modelOutputDir);

        String templatePath = "/com/secsoft/cms/common/generator/";
        gen.setModelTemplate(templatePath.concat("model_template.jf"));
        gen.setBaseModelTemplate(templatePath.concat("base_model_template.jf"));
        gen.setMappingKitTemplate(templatePath.concat("mapping_kit_template.jf"));

        // 设置 BaseModel 是否生成链式 setter 方法
        gen.setGenerateChainSetter(true);

        // 设置是否在 Model 中生成 dao 对象
        gen.setGenerateDaoInModel(false);

        // 设置是否生成字典文件
        gen.setGenerateDataDictionary(true);

        // 创建元信息
        MetaBuilder metaBuilder = new MetaBuilder(dataSource);

        // 设置数据库方言
        metaBuilder.setDialect(new Sqlite3Dialect());

        // 设置是否生成字段备注
        metaBuilder.setGenerateRemarks(true);

        // 设置需要排除的标表
        metaBuilder.addExcludedTable(EXCLUDED_TABLE);

        // 设置需要被移除的表名前缀用于生成modelName
        metaBuilder.setRemovedTableNamePrefixes("auth_");

        // 设置元信息
        gen.setMetaBuilder(metaBuilder);

        // 生成
        gen.generate();
    }

}