import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceService } from './sale-invoice.service';

@Component({
    selector: 'jhi-sale-invoice-delete-dialog',
    templateUrl: './sale-invoice-delete-dialog.component.html'
})
export class SaleInvoiceDeleteDialogComponent {
    saleInvoice: ISaleInvoice;

    constructor(
        private saleInvoiceService: SaleInvoiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleInvoiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleInvoiceListModification',
                content: 'Deleted an saleInvoice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-invoice-delete-popup',
    template: ''
})
export class SaleInvoiceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaleInvoiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.saleInvoice = saleInvoice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
