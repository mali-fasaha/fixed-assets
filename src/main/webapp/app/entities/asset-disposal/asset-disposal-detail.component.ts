import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAssetDisposal } from 'app/shared/model/asset-disposal.model';

@Component({
  selector: 'gha-asset-disposal-detail',
  templateUrl: './asset-disposal-detail.component.html'
})
export class AssetDisposalDetailComponent implements OnInit {
  assetDisposal: IAssetDisposal;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetDisposal }) => {
      this.assetDisposal = assetDisposal;
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
