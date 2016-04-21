package com.xmartlabs.template.model;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by mirland on 20/04/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public abstract class DataBaseModel extends BaseModel {
  @PrimaryKey(autoincrement = true)
  String id;
}
