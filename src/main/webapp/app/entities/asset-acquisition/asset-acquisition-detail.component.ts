import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';

@Component({
  selector: 'gha-asset-acquisition-detail',
  templateUrl: './asset-acquisition-detail.component.html'
})
export class AssetAcquisitionDetailComponent implements OnInit {
  assetAcquisition: IAssetAcquisition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetAcquisition }) => {
      this.assetAcquisition = assetAcquisition;
    });
  }

  previousState() {
    window.history.back();
  }
}
