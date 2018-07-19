import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';
import { SaleInvoiceItemService } from './sale-invoice-item.service';

@Component({
    selector: 'jhi-sale-invoice-item-delete-dialog',
    templateUrl: './sale-invoice-item-delete-dialog.component.html'
})
export class SaleInvoiceItemDeleteDialogComponent {
    saleInvoiceItem: ISaleInvoiceItem;

    constructor(
        private saleInvoiceItemService: SaleInvoiceItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleInvoiceItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleInvoiceItemListModification',
                content: 'Deleted an saleInvoiceItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-invoice-item-delete-popup',
    template: ''
})
export class SaleInvoiceItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoiceItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaleInvoiceItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.saleInvoiceItem = saleInvoiceItem;
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
