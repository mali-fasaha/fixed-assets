/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { DepreciationRegimeDeleteDialogComponent } from 'app/entities/depreciation-regime/depreciation-regime-delete-dialog.component';
import { DepreciationRegimeService } from 'app/entities/depreciation-regime/depreciation-regime.service';

describe('Component Tests', () => {
  describe('DepreciationRegime Management Delete Component', () => {
    let comp: DepreciationRegimeDeleteDialogComponent;
    let fixture: ComponentFixture<DepreciationRegimeDeleteDialogComponent>;
    let service: DepreciationRegimeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [DepreciationRegimeDeleteDialogComponent]
      })
        .overrideTemplate(DepreciationRegimeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepreciationRegimeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepreciationRegimeService);
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
