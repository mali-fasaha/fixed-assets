/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetCategoryDeleteDialogComponent } from 'app/entities/fixed-asset-category/fixed-asset-category-delete-dialog.component';
import { FixedAssetCategoryService } from 'app/entities/fixed-asset-category/fixed-asset-category.service';

describe('Component Tests', () => {
  describe('FixedAssetCategory Management Delete Component', () => {
    let comp: FixedAssetCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<FixedAssetCategoryDeleteDialogComponent>;
    let service: FixedAssetCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetCategoryDeleteDialogComponent]
      })
        .overrideTemplate(FixedAssetCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetCategoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
