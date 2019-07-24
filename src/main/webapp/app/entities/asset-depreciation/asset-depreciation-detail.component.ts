import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssetDepreciation } from 'app/shared/model/asset-depreciation.model';

@Component({
  selector: 'gha-asset-depreciation-detail',
  templateUrl: './asset-depreciation-detail.component.html'
})
export class AssetDepreciationDetailComponent implements OnInit {
  assetDepreciation: IAssetDepreciation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetDepreciation }) => {
      this.assetDepreciation = assetDepreciation;
    });
  }

  previousState() {
    window.history.back();
  }
}
