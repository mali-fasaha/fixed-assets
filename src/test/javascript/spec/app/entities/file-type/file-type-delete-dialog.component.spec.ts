/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FileTypeDeleteDialogComponent } from 'app/entities/file-type/file-type-delete-dialog.component';
import { FileTypeService } from 'app/entities/file-type/file-type.service';

describe('Component Tests', () => {
  describe('FileType Management Delete Component', () => {
    let comp: FileTypeDeleteDialogComponent;
    let fixture: ComponentFixture<FileTypeDeleteDialogComponent>;
    let service: FileTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FileTypeDeleteDialogComponent]
      })
        .overrideTemplate(FileTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileTypeService);
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
