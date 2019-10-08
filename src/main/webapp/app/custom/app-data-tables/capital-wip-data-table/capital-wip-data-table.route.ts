import { CapitalWorkInProgress, ICapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { CapitalWorkInProgressUpdateComponent } from 'app/entities/capital-work-in-progress/capital-work-in-progress-update.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs/index';
import { CapitalWorkInProgressDetailComponent } from 'app/entities/capital-work-in-progress/capital-work-in-progress-detail.component';
import { CapitalWorkInProgressDeletePopupComponent } from 'app/entities/capital-work-in-progress';
import { CapitalWorkInProgressDataTableService } from 'app/custom/app-data-tables/capital-wip-data-table/capital-wip-data-table.service';
import { CapitalWipDataTableComponent } from 'app/custom/app-data-tables/capital-wip-data-table/capital-wip-data-table.component';

@Injectable({ providedIn: 'root' })
export class CapitalWorkInProgressResolve implements Resolve<ICapitalWorkInProgress> {
  constructor(private service: CapitalWorkInProgressDataTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICapitalWorkInProgress> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CapitalWorkInProgress>) => response.ok),
        map((capitalWorkInProgress: HttpResponse<CapitalWorkInProgress>) => capitalWorkInProgress.body)
      );
    }
    return of(new CapitalWorkInProgress());
  }
}

export const capitalWorkInProgressDataTablesRoute: Routes = [
  {
    path: 'capital-work-in-progress',
    component: CapitalWipDataTableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Capital-Work-In-Progress'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CapitalWorkInProgressDetailComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CapitalWorkInProgressUpdateComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CapitalWorkInProgressUpdateComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const capitalWorkInProgressDataTablesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CapitalWorkInProgressDeletePopupComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
