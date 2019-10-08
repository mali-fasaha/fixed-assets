import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { AssetDepreciationDetailComponent } from 'app/entities/asset-depreciation/asset-depreciation-detail.component';
import { filter, map } from 'rxjs/operators';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs/index';
import { AssetDepreciationUpdateComponent } from 'app/entities/asset-depreciation/asset-depreciation-update.component';
import { AssetDepreciation, IAssetDepreciation } from 'app/shared/model/asset-depreciation.model';
import { AssetDepreciationDataTableService } from 'app/custom/app-data-tables/asset-depreciation-data-table/asset-depreciation-data-table.service';
import { AssetDepreciationDeletePopupComponent } from 'app/entities/asset-depreciation';
import { AssetDepreciationDataTableComponent } from 'app/custom/app-data-tables/asset-depreciation-data-table/asset-depreciation-data-table.component';

@Injectable({ providedIn: 'root' })
export class AssetDepreciationResolve implements Resolve<IAssetDepreciation> {
  constructor(private service: AssetDepreciationDataTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAssetDepreciation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AssetDepreciation>) => response.ok),
        map((assetDepreciation: HttpResponse<AssetDepreciation>) => assetDepreciation.body)
      );
    }
    return of(new AssetDepreciation());
  }
}

export const assetDepreciationDataTableRoute: Routes = [
  {
    path: 'asset-depreciation',
    component: AssetDepreciationDataTableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Asset Depreciation'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssetDepreciationDetailComponent,
    resolve: {
      assetDepreciation: AssetDepreciationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDepreciations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssetDepreciationUpdateComponent,
    resolve: {
      assetDepreciation: AssetDepreciationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDepreciations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssetDepreciationUpdateComponent,
    resolve: {
      assetDepreciation: AssetDepreciationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDepreciations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const assetDepreciationDataTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AssetDepreciationDeletePopupComponent,
    resolve: {
      assetDepreciation: AssetDepreciationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetDepreciations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
