/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { ScannedDocumentDeleteDialogComponent } from 'app/entities/scanned-document/scanned-document-delete-dialog.component';
import { ScannedDocumentService } from 'app/entities/scanned-document/scanned-document.service';

describe('Component Tests', () => {
  describe('ScannedDocument Management Delete Component', () => {
    let comp: ScannedDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<ScannedDocumentDeleteDialogComponent>;
    let service: ScannedDocumentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [ScannedDocumentDeleteDialogComponent]
      })
        .overrideTemplate(ScannedDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScannedDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScannedDocumentService);
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
