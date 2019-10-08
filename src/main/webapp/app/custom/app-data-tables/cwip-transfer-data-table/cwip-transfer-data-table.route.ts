import { CwipTransferComponent } from 'app/entities/cwip-transfer/cwip-transfer.component';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { CwipTransfer, ICwipTransfer } from 'app/shared/model/cwip-transfer.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { CwipTransferDetailComponent } from 'app/entities/cwip-transfer/cwip-transfer-detail.component';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { CwipTransferService } from 'app/entities/cwip-transfer/cwip-transfer.service';
import { CwipTransferUpdateComponent } from 'app/entities/cwip-transfer/cwip-transfer-update.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs/index';
import { CwipTransferDeletePopupComponent } from 'app/entities/cwip-transfer';
import { CwipTransferDataTableComponent } from 'app/custom/app-data-tables/cwip-transfer-data-table/cwip-transfer-data-table.component';

@Injectable({ providedIn: 'root' })
export class CwipTransferResolve implements Resolve<ICwipTransfer> {
  constructor(private service: CwipTransferService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICwipTransfer> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CwipTransfer>) => response.ok),
        map((cwipTransfer: HttpResponse<CwipTransfer>) => cwipTransfer.body)
      );
    }
    return of(new CwipTransfer());
  }
}

export const cwipTransferDataTableRoute: Routes = [
  {
    path: 'capital-work-in-progress-transfer',
    component: CwipTransferDataTableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Capital WIP Transfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CwipTransferDetailComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CwipTransferUpdateComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CwipTransferUpdateComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cwipTransferDataTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CwipTransferDeletePopupComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
