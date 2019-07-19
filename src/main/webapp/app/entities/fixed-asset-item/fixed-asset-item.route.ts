import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FixedAssetItem } from 'app/shared/model/fixed-asset-item.model';
import { FixedAssetItemService } from './fixed-asset-item.service';
import { FixedAssetItemComponent } from './fixed-asset-item.component';
import { FixedAssetItemDetailComponent } from './fixed-asset-item-detail.component';
import { FixedAssetItemUpdateComponent } from './fixed-asset-item-update.component';
import { FixedAssetItemDeletePopupComponent } from './fixed-asset-item-delete-dialog.component';
import { IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';

@Injectable({ providedIn: 'root' })
export class FixedAssetItemResolve implements Resolve<IFixedAssetItem> {
  constructor(private service: FixedAssetItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFixedAssetItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FixedAssetItem>) => response.ok),
        map((fixedAssetItem: HttpResponse<FixedAssetItem>) => fixedAssetItem.body)
      );
    }
    return of(new FixedAssetItem());
  }
}

export const fixedAssetItemRoute: Routes = [
  {
    path: '',
    component: FixedAssetItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FixedAssetItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FixedAssetItemDetailComponent,
    resolve: {
      fixedAssetItem: FixedAssetItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FixedAssetItemUpdateComponent,
    resolve: {
      fixedAssetItem: FixedAssetItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FixedAssetItemUpdateComponent,
    resolve: {
      fixedAssetItem: FixedAssetItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fixedAssetItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FixedAssetItemDeletePopupComponent,
    resolve: {
      fixedAssetItem: FixedAssetItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
