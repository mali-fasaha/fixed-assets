import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs/index';
import { AssetItemDataTableComponent } from 'app/custom/app-data-tables/asset-item-data-table/asset-item-data-table.component';
import { FixedAssetItem, IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';
import {
  FixedAssetItemDeletePopupComponent,
  FixedAssetItemDetailComponent,
  FixedAssetItemUpdateComponent
} from 'app/entities/fixed-asset-item';

@Injectable({ providedIn: 'root' })
export class AssetItemDataTableResolve implements Resolve<IFixedAssetItem> {
  // TODO Implement and apply AssetItemDataTableService
  constructor(private service: AssetItemDataTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFixedAssetItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FixedAssetItem>) => response.ok),
        map((AssetItem: HttpResponse<FixedAssetItem>) => AssetItem.body)
      );
    }
    return of(new FixedAssetItem());
  }
}

// TODO Review routes
export const assetItemDataTableRoute: Routes = [
  {
    path: 'asset-item',
    component: AssetItemDataTableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Asset Acquisition Data'
    },
    canActivate: [UserRouteAccessService]
  }
];

// TODO Replace with date query popup
export const AssetItemDataTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FixedAssetItemDeletePopupComponent,
    resolve: {
      AssetItem: AssetItemDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  // TODO Implement pop up components for these paths
  {
    path: ':id/view',
    component: FixedAssetItemDetailComponent,
    resolve: {
      AssetItem: AssetItemDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Asset Acquisitions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FixedAssetItemUpdateComponent,
    resolve: {
      AssetItem: AssetItemDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FixedAssetItemUpdateComponent,
    resolve: {
      AssetItem: AssetItemDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetItems'
    },
    canActivate: [UserRouteAccessService]
  }
];
