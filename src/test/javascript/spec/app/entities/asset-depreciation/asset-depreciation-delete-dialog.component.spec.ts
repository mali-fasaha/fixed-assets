/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetDepreciationDeleteDialogComponent } from 'app/entities/asset-depreciation/asset-depreciation-delete-dialog.component';
import { AssetDepreciationService } from 'app/entities/asset-depreciation/asset-depreciation.service';

describe('Component Tests', () => {
  describe('AssetDepreciation Management Delete Component', () => {
    let comp: AssetDepreciationDeleteDialogComponent;
    let fixture: ComponentFixture<AssetDepreciationDeleteDialogComponent>;
    let service: AssetDepreciationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetDepreciationDeleteDialogComponent]
      })
        .overrideTemplate(AssetDepreciationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetDepreciationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetDepreciationService);
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
