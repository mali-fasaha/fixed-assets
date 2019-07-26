import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FileType } from 'app/shared/model/file-type.model';
import { FileTypeService } from './file-type.service';
import { FileTypeComponent } from './file-type.component';
import { FileTypeDetailComponent } from './file-type-detail.component';
import { FileTypeUpdateComponent } from './file-type-update.component';
import { FileTypeDeletePopupComponent } from './file-type-delete-dialog.component';
import { IFileType } from 'app/shared/model/file-type.model';

@Injectable({ providedIn: 'root' })
export class FileTypeResolve implements Resolve<IFileType> {
  constructor(private service: FileTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFileType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FileType>) => response.ok),
        map((fileType: HttpResponse<FileType>) => fileType.body)
      );
    }
    return of(new FileType());
  }
}

export const fileTypeRoute: Routes = [
  {
    path: '',
    component: FileTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FileTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileTypeDetailComponent,
    resolve: {
      fileType: FileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileTypeUpdateComponent,
    resolve: {
      fileType: FileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileTypeUpdateComponent,
    resolve: {
      fileType: FileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fileTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FileTypeDeletePopupComponent,
    resolve: {
      fileType: FileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
