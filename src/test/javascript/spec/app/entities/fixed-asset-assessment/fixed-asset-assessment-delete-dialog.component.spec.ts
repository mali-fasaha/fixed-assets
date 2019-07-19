/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetAssessmentDeleteDialogComponent } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment-delete-dialog.component';
import { FixedAssetAssessmentService } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment.service';

describe('Component Tests', () => {
  describe('FixedAssetAssessment Management Delete Component', () => {
    let comp: FixedAssetAssessmentDeleteDialogComponent;
    let fixture: ComponentFixture<FixedAssetAssessmentDeleteDialogComponent>;
    let service: FixedAssetAssessmentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetAssessmentDeleteDialogComponent]
      })
        .overrideTemplate(FixedAssetAssessmentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetAssessmentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetAssessmentService);
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
