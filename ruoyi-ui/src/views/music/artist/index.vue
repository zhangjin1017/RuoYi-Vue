<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="别名" prop="alias">
        <el-input
          v-model="queryParams.alias"
          placeholder="请输入别名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专辑数" prop="albumSize">
        <el-input
          v-model="queryParams.albumSize"
          placeholder="请输入专辑数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份" prop="identities">
        <el-input
          v-model="queryParams.identities"
          placeholder="请输入身份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="歌曲数" prop="musicSize">
        <el-input
          v-model="queryParams.musicSize"
          placeholder="请输入歌曲数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Mv数" prop="mvSize">
        <el-input
          v-model="queryParams.mvSize"
          placeholder="请输入Mv数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['music:artist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['music:artist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['music:artist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:artist:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="artistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="作者id" align="center" prop="artistId" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="别名" align="center" prop="alias" >
        <template slot-scope="scope">
        <span>{{ scope.row.alias.length!=2 ? scope.row.alias : '无' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="封面" align="center" prop="coverImg" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="专辑数" align="center" prop="albumSize" />
      <el-table-column label="身份" align="center" prop="identities" >
        <template slot-scope="scope">
        <span>{{ scope.row.identities.length!=2 ? scope.row.identities : '无' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="歌曲数" align="center" prop="musicSize" />
      <el-table-column label="Mv数" align="center" prop="mvSize" />
      <el-table-column label="主要描述" align="center" prop="briefDesc">
        <template slot-scope="scope">
        <div class="table-cell-container">
          <div class="table-cell-content">{{ scope.row.briefDesc }}</div>
        </div>
      </template>
      </el-table-column>
      <el-table-column label="介绍" align="center" prop="introduction" >
        <template slot-scope="scope">
        <div class="table-cell-container">
          <div class="table-cell-content">{{ scope.row.introduction.length!=2 ? scope.row.introduction : '无' }}</div>
        </div>
      </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:artist:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:artist:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改作者信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="别名" prop="alias">
          <el-input v-model="form.alias" placeholder="请输入别名" />
        </el-form-item>
        <el-form-item label="封面" prop="coverImg">
          <image-upload v-model="form.coverImg"/>
        </el-form-item>
        <el-form-item label="专辑数" prop="albumSize">
          <el-input v-model="form.albumSize" placeholder="请输入专辑数" />
        </el-form-item>
        <el-form-item label="身份" prop="identities">
          <el-input v-model="form.identities" placeholder="请输入身份" />
        </el-form-item>
        <el-form-item label="歌曲数" prop="musicSize">
          <el-input v-model="form.musicSize" placeholder="请输入歌曲数" />
        </el-form-item>
        <el-form-item label="Mv数" prop="mvSize">
          <el-input v-model="form.mvSize" placeholder="请输入Mv数" />
        </el-form-item>
        <el-form-item label="主要描述" prop="briefDesc">
          <el-input v-model="form.briefDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="介绍" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArtist, getArtist, delArtist, addArtist, updateArtist } from "@/api/music/artist";

export default {
  name: "Artist",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 作者信息表格数据
      artistList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        alias: null,
        albumSize: null,
        identities: null,
        musicSize: null,
        mvSize: null,
        briefDesc: null,
        introduction: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询作者信息列表 */
    getList() {
      this.loading = true;
      listArtist(this.queryParams).then(response => {
        this.artistList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        artistId: null,
        name: null,
        alias: null,
        coverImg: null,
        albumSize: null,
        identities: null,
        musicSize: null,
        mvSize: null,
        briefDesc: null,
        introduction: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.artistId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作者信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const artistId = row.artistId || this.ids
      getArtist(artistId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改作者信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.artistId != null) {
            updateArtist(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArtist(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const artistIds = row.artistId || this.ids;
      this.$modal.confirm('是否确认删除作者信息编号为"' + artistIds + '"的数据项？').then(function() {
        return delArtist(artistIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/artist/export', {
        ...this.queryParams
      }, `artist_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.table-cell-container {
  height: 100px;
  overflow: auto;
}
.table-cell-content {
  white-space: pre-wrap;
}
</style>