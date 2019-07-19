import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';

@Component({
  selector: 'gha-fixed-asset-item-detail',
  templateUrl: './fixed-asset-item-detail.component.html'
})
export class FixedAssetItemDetailComponent implements OnInit {
  fixedAssetItem: IFixedAssetItem;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetItem }) => {
      this.fixedAssetItem = fixedAssetItem;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
