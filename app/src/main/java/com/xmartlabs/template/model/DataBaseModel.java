package com.xmartlabs.template.model;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public abstract class DataBaseModel extends BaseModel {
  @PrimaryKey(autoincrement = true)
  String id;
}
