/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoiceDeleteDialogComponent } from 'app/entities/sale-invoice/sale-invoice-delete-dialog.component';
import { SaleInvoiceService } from 'app/entities/sale-invoice/sale-invoice.service';

describe('Component Tests', () => {
    describe('SaleInvoice Management Delete Component', () => {
        let comp: SaleInvoiceDeleteDialogComponent;
        let fixture: ComponentFixture<SaleInvoiceDeleteDialogComponent>;
        let service: SaleInvoiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoiceDeleteDialogComponent]
            })
                .overrideTemplate(SaleInvoiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleInvoiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleInvoiceService);
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
