/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { SupplierDeleteDialogComponent } from 'app/entities/supplier/supplier-delete-dialog.component';
import { SupplierService } from 'app/entities/supplier/supplier.service';

describe('Component Tests', () => {
    describe('Supplier Management Delete Component', () => {
        let comp: SupplierDeleteDialogComponent;
        let fixture: ComponentFixture<SupplierDeleteDialogComponent>;
        let service: SupplierService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SupplierDeleteDialogComponent]
            })
                .overrideTemplate(SupplierDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplierDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplierService);
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
