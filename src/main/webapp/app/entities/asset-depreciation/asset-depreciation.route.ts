import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AssetDepreciation } from 'app/shared/model/asset-depreciation.model';
import { AssetDepreciationService } from './asset-depreciation.service';
import { AssetDepreciationComponent } from './asset-depreciation.component';
import { AssetDepreciationDetailComponent } from './asset-depreciation-detail.component';
import { AssetDepreciationUpdateComponent } from './asset-depreciation-update.component';
import { AssetDepreciationDeletePopupComponent } from './asset-depreciation-delete-dialog.component';
import { IAssetDepreciation } from 'app/shared/model/asset-depreciation.model';

@Injectable({ providedIn: 'root' })
export class AssetDepreciationResolve implements Resolve<IAssetDepreciation> {
  constructor(private service: AssetDepreciationService) {}

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

export const assetDepreciationRoute: Routes = [
  {
    path: '',
    component: AssetDepreciationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AssetDepreciations'
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

export const assetDepreciationPopupRoute: Routes = [
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
