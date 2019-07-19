import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFixedAssetCategory } from 'app/shared/model/fixed-asset-category.model';

@Component({
  selector: 'gha-fixed-asset-category-detail',
  templateUrl: './fixed-asset-category-detail.component.html'
})
export class FixedAssetCategoryDetailComponent implements OnInit {
  fixedAssetCategory: IFixedAssetCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetCategory }) => {
      this.fixedAssetCategory = fixedAssetCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
